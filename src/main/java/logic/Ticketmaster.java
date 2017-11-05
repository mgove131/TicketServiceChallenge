package main.java.logic;

import main.java.model.Venue;

/**
 * Handles the logic of reserving seats.
 * 
 * @author User
 *
 */
public final class Ticketmaster {

	/**
	 * Constructor.
	 */
	public Ticketmaster() {
		this.venue = Factory.getVenue();
	}

	private Venue venue;

}
