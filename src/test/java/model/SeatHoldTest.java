package test.java.model;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.model.Seat;
import main.java.model.SeatHold;

public class SeatHoldTest {

	private static SeatHold same1;
	private static SeatHold same2;
	private static SeatHold different;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		same1 = new SeatHold(0, "0", Calendar.getInstance(), new Seat[0]);
		same2 = new SeatHold(same1.getId(), same1.getEmail(), same1.getExpireDate(), same1.getSeats());
		different = new SeatHold(1, "1", Calendar.getInstance(), new Seat[0]);
	}

	@Test
	public void testHashCode() {
		Assert.assertEquals(same1.hashCode(), same2.hashCode());
		Assert.assertNotEquals(same1.hashCode(), different.hashCode());
	}

	@Test
	public void testGetId() {
		Assert.assertEquals(same1.getId(), same2.getId());
		Assert.assertNotEquals(same1.getId(), different.getId());
	}

	@Test
	public void testGetEmail() {
		Assert.assertEquals(same1.getEmail(), same2.getEmail());
		Assert.assertNotEquals(same1.getEmail(), different.getEmail());
	}

	@Test
	public void testGetExpireDate() {
		Assert.assertEquals(same1.getExpireDate(), same2.getExpireDate());
		Assert.assertNotEquals(same1.getExpireDate(), different.getExpireDate());
	}

	@Test
	public void testGetSeats() {
		Assert.assertArrayEquals(same1.getSeats(), same2.getSeats());
		Assert.assertNotEquals(same1.getSeats(), different.getSeats());
	}

	@Test
	public void testEqualsObject() {
		Assert.assertEquals(same1, same1);
		Assert.assertEquals(same1, same2);
		Assert.assertNotEquals(same1, different);
		Assert.assertNotEquals(same1, null);
		Assert.assertNotEquals(same1, new Object());
	}
}
