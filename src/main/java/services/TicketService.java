package main.java.services;

import java.io.Closeable;

import main.java.logic.Ticketmaster;
import main.java.model.SeatHold;

/**
 * TicketService interface.
 * 
 * @author User
 *
 */
public interface TicketService extends Closeable {

	/**
	 * Get the backing Ticketmaster.
	 * 
	 * @return Ticketmaster
	 */
	Ticketmaster getTicketmaster();

	/**
	 * The number of seats in the venue that are neither held nor reserved
	 *
	 * @return the number of tickets available in the venue
	 */
	int numSeatsAvailable();

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
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);

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
	String reserveSeats(int seatHoldId, String customerEmail);
}
