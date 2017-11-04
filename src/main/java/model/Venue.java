package main.java.model;

/**
 * A venue is a collection of {@link Seat}s.
 * 
 * @author User
 *
 */
public class Venue {

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            Number of rows.
	 * @param colCount
	 *            Number of seats per row.
	 */
	public Venue(int rowCount, int colCount) {
		this.seats = new Seat[rowCount][colCount];
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
