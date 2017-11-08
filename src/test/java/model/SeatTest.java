package test.java.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.model.Seat;

public class SeatTest {

	private static Seat same1;
	private static Seat same2;
	private static Seat different;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		same1 = new Seat(1, 2);
		same2 = new Seat(1, 2);
		different = new Seat(3, 4);
	}

	@Test
	public void testHashCode() {
		Assert.assertEquals(same1.hashCode(), same2.hashCode());
		Assert.assertNotEquals(same1.hashCode(), different.hashCode());
	}

	@Test
	public void testGetRow() {
		Assert.assertEquals(same1.getRow(), same2.getRow());
		Assert.assertNotEquals(same1.getRow(), different.getRow());
	}

	@Test
	public void testGetCol() {
		Assert.assertEquals(same1.getCol(), same2.getCol());
		Assert.assertNotEquals(same1.getCol(), different.getCol());
	}

	@Test
	public void testEqualsObject() {
		Assert.assertEquals(same1, same1);
		Assert.assertEquals(same1, same2);
		Assert.assertNotEquals(same1, different);
		Assert.assertNotEquals(same1, null);
		Assert.assertNotEquals(same1, new Object());
	}

	@Test
	public void testToString() {
		Assert.assertEquals(same1.toString(), same2.toString());
		Assert.assertNotEquals(same1.toString(), different.toString());
	}

}
