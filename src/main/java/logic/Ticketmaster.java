package main.java.logic;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import main.java.model.Seat;
import main.java.model.SeatHold;
import main.java.model.Venue;

/**
 * Handles the logic of reserving seats.
 * 
 * Singleton, use getInstance() to get the instance.
 * 
 * Please call close() when ending the application.
 * 
 * @author User
 *
 */
public final class Ticketmaster implements Closeable {

	private static final int DEFAULT_EXPIRATION_SECONDS = 5;

	/**
	 * Constructor.
	 * 
	 * @param holdTimeoutSeconds
	 *            Timeout for holding seats.
	 */
	public Ticketmaster(int holdTimeoutSeconds) {
		this.isRunning = true;
		this.holdTimeoutSeconds = holdTimeoutSeconds;
		this.seatHoldIdCounter = 0;
		this.venue = Factory.getVenue();
		this.holds = new HashMap<Integer, SeatHold>();
		this.reservations = new ArrayList<SeatHold>();

		threadHoldCleaner = new Thread(new ReservationCleaner());
		threadHoldCleaner.start();
	}

	/**
	 * Constructor.
	 * 
	 * Uses default timeout.
	 */
	public Ticketmaster() {
		this(DEFAULT_EXPIRATION_SECONDS);
	}

	private final Object lock = new Object();

	private final Thread threadHoldCleaner;

	private volatile boolean isRunning;

	private int holdTimeoutSeconds;

	public int getHoldTimeoutSeconds() {
		return holdTimeoutSeconds;
	}

	private int seatHoldIdCounter;

	private Venue venue;

	public Venue getVenue() {
		return venue;
	}

	private Map<Integer, SeatHold> holds;

	public List<SeatHold> getSeatHolds() {
		List<SeatHold> returnValue = new ArrayList<SeatHold>();
		returnValue.addAll(holds.values());
		return returnValue;
	}

	private List<SeatHold> reservations;

	public List<SeatHold> getSeatReservations() {
		List<SeatHold> returnValue = new ArrayList<SeatHold>();
		returnValue.addAll(reservations);
		return returnValue;
	}

	@Override
	public void close() {
		isRunning = false;
		try {
			threadHoldCleaner.join();
		} catch (InterruptedException e) {
		}
	}

	private Calendar getNextExpirationTime() {
		Calendar exp = Calendar.getInstance();
		exp.add(Calendar.SECOND, holdTimeoutSeconds);
		return exp;
	}

	/**
	 * The number of seats in the venue that are neither held nor reserved
	 *
	 * @return the number of tickets available in the venue
	 */
	public int numSeatsAvailable() {
		int seatsAvailable = venue.getNumberOfSeats();

		synchronized (lock) {
			for (SeatHold sh : holds.values()) {
				seatsAvailable -= sh.getSeats().length;
			}

			for (SeatHold sh : reservations) {
				seatsAvailable -= sh.getSeats().length;
			}
		}

		return seatsAvailable;
	}

	/**
	 * Find and hold the best available seats for a customer
	 *
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 *         information. Null in error conditions.
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// null is returned if there are not enough seats
		SeatHold seathold = null;

		if (customerEmail == null) {
			customerEmail = "";
		}

		synchronized (lock) {
			if ((numSeats > 0) && (numSeatsAvailable() >= numSeats)) {
				List<Seat> unavailableSeats = new ArrayList<Seat>();
				for (SeatHold sh : holds.values()) {
					unavailableSeats.addAll(Arrays.asList(sh.getSeats()));
				}
				for (SeatHold sh : reservations) {
					unavailableSeats.addAll(Arrays.asList(sh.getSeats()));
				}

				List<Seat> seatsToHold = new ArrayList<Seat>();
				final int numRows = venue.getRowCount();
				final int seatsInARow = venue.getColCount();
				int searchSize = (numSeats < seatsInARow) ? numSeats : seatsInARow;
				outerLoop: while (searchSize > 0) {

					rowLoop: for (int r = 0; r < numRows; r++) {
						colLoop: for (int c = 0; c < seatsInARow; c++) {

							if ((c + searchSize) > seatsInARow) {
								break colLoop;
							}

							List<Seat> candidateSeats = new ArrayList<Seat>();
							for (int i = 0; i < searchSize; i++) {
								final int seatInd = c + i;
								Seat candidateSeat = new Seat(r, seatInd);
								candidateSeats.add(candidateSeat);
							}

							// make sure candidate seats are available
							if ((Collections.disjoint(candidateSeats, seatsToHold))
									&& (Collections.disjoint(candidateSeats, unavailableSeats))) {

								// can hold these seats
								seatsToHold.addAll(candidateSeats);
								// if all seats are found, stop looking
								if (seatsToHold.size() == numSeats) {
									break outerLoop;
								}

								// can skip ahead
								c = c + searchSize - 1;

								// make sure we are looking for the right size
								int seatsLeftToFind = numSeats - seatsToHold.size();
								if (seatsLeftToFind < searchSize) {
									// +1 because it is going to decrement before looping
									searchSize = seatsLeftToFind + 1;
									// start from the first row with the new size
									break rowLoop;
								}
							}
						}
					}

					searchSize--;
				}

				if (seatsToHold.size() == numSeats) {
					seathold = new SeatHold(seatHoldIdCounter, customerEmail, getNextExpirationTime(),
							seatsToHold.toArray(new Seat[seatsToHold.size()]));
					seatHoldIdCounter++;

					holds.put(seathold.getId(), seathold);
				}
			}
		}

		return seathold;
	}

	/**
	 * Commit seats held for a specific customer
	 *
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code. Null in error conditions.
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// null is returned if there is no valid hold
		String returnValue = null;

		if (customerEmail == null) {
			customerEmail = "";
		}

		synchronized (lock) {
			if (holds.containsKey(seatHoldId)) {
				SeatHold sh = holds.get(seatHoldId);

				// also check email to make sure it is valid
				if (customerEmail.equals(sh.getEmail())) {
					holds.remove(seatHoldId);
					reservations.add(sh);

					returnValue = UUID.randomUUID().toString();
				}
			}
		}

		return returnValue;
	}

	/**
	 * Holds need to expire.
	 * 
	 * @author User
	 *
	 */
	class ReservationCleaner implements Runnable {
		@Override
		public void run() {
			outerLoop: while (isRunning) {
				synchronized (lock) {
					Iterator<Entry<Integer, SeatHold>> it = holds.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Integer, SeatHold> pair = it.next();
						SeatHold sh = pair.getValue();
						if (sh.isExpired()) {
							it.remove(); // avoids a ConcurrentModificationException
						}
					}
				}

				try {
					for (int i = 0; i < 4; i++) {
						Thread.sleep(250);
						if (!isRunning) {
							break outerLoop;
						}
					}
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
