package main.java.logic;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private static final Object LOCK = new Object();

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

	private List<SeatHold> reservations;

	@Override
	public void close() {
		isRunning = false;
		try {
			threadHoldCleaner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
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

		synchronized (LOCK) {
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
	 *         information
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// null is returned if there are not enough seats
		SeatHold seathold = null;

		synchronized (LOCK) {
			if (numSeatsAvailable() >= numSeats) {
				List<Seat> unavailableSeats = new ArrayList<Seat>();
				for (SeatHold sh : holds.values()) {
					unavailableSeats.addAll(Arrays.asList(sh.getSeats()));
				}
				for (SeatHold sh : reservations) {
					unavailableSeats.addAll(Arrays.asList(sh.getSeats()));
				}

				List<Seat> seatsToHold = new ArrayList<Seat>();
				outer: for (int r = 0; r < venue.getRowCount(); r++) {
					for (int c = 0; c <= (venue.getColCount() - numSeats); c++) {
						seatsToHold.clear();

						// check to see if all the seats are available
						for (int i = 0; i <= numSeats; i++) {
							Seat s = new Seat(r, c + i);

							if (unavailableSeats.contains(s)) {
								continue;
							} else {
								seatsToHold.add(s);

								// if we filled up all the seats
								if (seatsToHold.size() == numSeats) {
									break outer;
								}
							}
						}
					}
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
	 * @return a reservation confirmation code
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// null is returned if there is no valid hold
		String returnValue = null;

		synchronized (LOCK) {
			if (holds.containsKey(seatHoldId)) {
				SeatHold sh = holds.get(seatHoldId);

				// also check email to make sure it is valid
				if (sh.getEmail().equals(customerEmail)) {
					holds.remove(seatHoldId);
					reservations.add(sh);
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
			while (isRunning) {
				synchronized (LOCK) {
					// use iterator to safely remove items
					Set<Integer> keys = holds.keySet();
					for (Integer key : keys) {
						SeatHold sh = holds.get(key);
						if (sh.isExpired()) {
							holds.remove(key);
						}
					}
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
