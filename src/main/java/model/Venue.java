package main.java.model;

/**
 * A venue represents rows of seats.
 * 
 * @author User
 *
 */
public final class Venue {

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            Number of rows.
	 * @param colCount
	 *            Number of seats per row.
	 */
	public Venue(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
	}

	/**
	 * Get total number of seats;
	 * 
	 * @return total number of seats;
	 */
	public int getNumberOfSeats() {
		return rowCount * colCount;
	}

	private int rowCount;

	public int getRowCount() {
		return rowCount;
	}

	private int colCount;

	public int getColCount() {
		return colCount;
	}
}
