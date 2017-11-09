package main.java.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
		return Calendar.getInstance().after(expireDate);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		SeatHold other = (SeatHold) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SeatHold [id=" + id + ", email=" + email + ", expireDate="
				+ new SimpleDateFormat("hh:mm:ss a").format(expireDate.getTime()) + ", seats=" + Arrays.toString(seats)
				+ "]";
	}

}
