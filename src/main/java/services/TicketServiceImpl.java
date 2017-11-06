package main.java.services;

import java.io.IOException;

import main.java.logic.Ticketmaster;
import main.java.model.SeatHold;

/**
 * TicketService implemented.
 * 
 * @author User
 *
 */
public final class TicketServiceImpl implements TicketService {

	/**
	 * Constructor.
	 * 
	 * @param holdTimeoutSeconds
	 *            Timeout for holding seats.
	 */
	public TicketServiceImpl(int holdTimeoutSeconds) {
		this.ticketmaster = new Ticketmaster(holdTimeoutSeconds);
	}

	/**
	 * Constructor.
	 */
	public TicketServiceImpl() {
		this.ticketmaster = new Ticketmaster();
	}

	private Ticketmaster ticketmaster;

	public Ticketmaster getTicketmaster() {
		return ticketmaster;
	}

	@Override
	public int numSeatsAvailable() {
		return ticketmaster.numSeatsAvailable();
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		return ticketmaster.findAndHoldSeats(numSeats, customerEmail);
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		return ticketmaster.reserveSeats(seatHoldId, customerEmail);
	}

	@Override
	public void close() throws IOException {
		ticketmaster.close();
	}
}
