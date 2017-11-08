package main.java.model;

import java.util.Calendar;

public final class SeatHold {

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            SeatHold id.
	 * @param email
	 *            Customer email.
	 * @param expireDate
	 *            When the hold expires.
	 * @param seats
	 *            Seats being held.
	 */
	public SeatHold(int id, String email, Calendar expireDate, Seat[] seats) {
		this.id = id;
		this.email = email;
		this.expireDate = expireDate;
		this.seats = seats;
	}

	/**
	 * Is the seat hold expired?
	 * 
	 * @return True if the current time is after the expiration.
	 */
	public boolean isExpired() {
		return Calendar.getInstance().after(getExpireDate());
	}

	private int id;

	public int getId() {
		return id;
	}

	private String email;

	public String getEmail() {
		return email;
	}

	private Calendar expireDate;

	public Calendar getExpireDate() {
		return expireDate;
	}

	private Seat[] seats;

	public Seat[] getSeats() {
		return seats;
	}
}
