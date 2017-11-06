package main.java.model;

/**
 * A venue is a collection of {@link Seat}s.
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
		this.seats = new Seat[rowCount][colCount];
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

	private Seat[][] seats;

	public Seat[][] getSeats() {
		return seats;
	}

	/**
	 * Get seat at (row, col).
	 * 
	 * @param row
	 *            row index
	 * @param col
	 *            seat index
	 * @return Seat at (row, col). (@code null} if out of bounds.
	 */
	public Seat getSeat(int row, int col) {
		if ((seats == null) || (row < 0) || (col < 0) || (row > seats.length) || (col > seats[row].length)) {
			return null;
		}
		return seats[row][col];
	}
}
