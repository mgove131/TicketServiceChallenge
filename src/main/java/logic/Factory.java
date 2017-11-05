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
	private Factory() {
		// shouldn't be able to create an instance
	}

	public static Venue getVenue() {
		VenueProvider vp = new VenueProviderDefault();
		return vp.getVenue();
	}
}
