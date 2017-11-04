package main.java.logic.venueproviders;

import main.java.model.Venue;

/**
 * Interface for a venue provider.
 * 
 * Using venue providers allows venue to be fetched from multiple sources.
 * 
 * @author User
 *
 */
public interface VenueProvider {

	/**
	 * Get a venue.
	 * 
	 * @return a Venue
	 */
	Venue getVenue();
}
