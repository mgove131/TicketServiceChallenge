package main.java.logic;

import main.java.logic.venueproviders.VenueProvider;
import main.java.logic.venueproviders.VenueProviderDefault;
import main.java.model.Venue;

/**
 * Handles the logic of reserving seats.
 * 
 * @author User
 *
 */
public class Ticketmaster {

	/**
	 * Constructor.
	 */
	public Ticketmaster() {
		VenueProvider vp = new VenueProviderDefault();
		this.venue = vp.getVenue();
	}

	private Venue venue;

}
