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
	 */
	public Seat() {
		super();
	}

	private SeatStatus status;

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}
}
