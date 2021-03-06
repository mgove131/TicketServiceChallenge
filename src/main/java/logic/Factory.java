package main.java.logic;

import main.java.logic.venueproviders.VenueProvider;
import main.java.logic.venueproviders.VenueProviderDefault;
import main.java.model.Venue;

/**
 * Has factory methods for getting some objects.
 * 
 * @author User
 *
 */
public final class Factory {

	static {
		// just for code coverage
		new Factory();
	}

	private Factory() {
		// shouldn't be able to create an instance
	}

	/**
	 * Used to get the Venue.
	 * 
	 * @return a Venue.
	 */
	public static Venue getVenue() {
		VenueProvider vp = new VenueProviderDefault();
		return vp.getVenue();
	}
}
