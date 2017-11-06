package main.java.model;

/**
 * Represents a reservable seat.
 * 
 * @author User
 *
 */
public final class Seat {

	/**
	 * Constructor.
	 * 
	 * @param row
	 *            row number
	 * @param col
	 *            seat number
	 */
	public Seat(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	private int row;

	public int getRow() {
		return row;
	}

	private int col;

	public int getCol() {
		return col;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
