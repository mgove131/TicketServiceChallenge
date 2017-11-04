package main.java.logic.venueproviders;

import main.java.model.Venue;

/**
 * Default implementation of a VenueProvider.
 * 
 * This just returns a hardcoded venue.
 * 
 * @author User
 *
 */
public class VenueProviderDefault implements VenueProvider {

	/**
	 * Number of rows being used.
	 */
	private static final int ROWS = 9;

	/**
	 * Seats per row being used.
	 */
	private static final int COLS = 33;

	/**
	 * This constructor uses static values for the number of rows and columns.
	 */
	public VenueProviderDefault() {
		this(VenueProviderDefault.ROWS, VenueProviderDefault.COLS);
	}

	/**
	 * Will provide a rectangular venue with the number of rows and cols provided.
	 * 
	 * @param rows
	 *            Number of rows.
	 * @param cols
	 *            Number of seats per row.
	 */
	public VenueProviderDefault(int rows, int cols) {
		super();

		this.rows = rows;
		this.cols = cols;
	}

	private int rows;

	private int cols;

	@Override
	public Venue getVenue() {
		Venue v = new Venue(rows, cols);
		return v;
	}
}
