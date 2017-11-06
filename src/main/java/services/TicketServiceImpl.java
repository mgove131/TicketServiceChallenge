package main.java.services;

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
	 */
	public TicketServiceImpl() {
		this.ticketmaster = Ticketmaster.getInstance();
	}

	private Ticketmaster ticketmaster;

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

}
