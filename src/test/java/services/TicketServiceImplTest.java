package test.java.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.java.model.Seat;
import main.java.model.SeatHold;
import main.java.services.TicketService;
import main.java.services.TicketServiceImpl;

public class TicketServiceImplTest {

	private TicketService serviceShortHold;
	private TicketService serviceDefaultHold;
	private TicketService serviceLongHold;
	private TicketService serviceVeryLongHold;

	@Before
	public void setUp() throws Exception {
		serviceShortHold = new TicketServiceImpl(1);
		serviceDefaultHold = new TicketServiceImpl();
		serviceLongHold = new TicketServiceImpl(60);
		serviceVeryLongHold = new TicketServiceImpl(999);
	}

	@After
	public void tearDown() throws Exception {
		if (serviceShortHold != null) {
			serviceShortHold.close();
		}

		if (serviceDefaultHold != null) {
			serviceDefaultHold.close();
		}

		if (serviceLongHold != null) {
			serviceLongHold.close();
		}

		if (serviceVeryLongHold != null) {
			serviceVeryLongHold.close();
		}
	}

	/**
	 * Call findAndHoldSeats for the service.
	 * 
	 * Returns SeatHold object.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param numSeatsToHold
	 *            number of seats to hold
	 * @param email
	 *            email address
	 * @return SeatHold
	 */
	private static SeatHold holdSeats(TicketService service, int numSeatsToHold, String email) {
		return service.findAndHoldSeats(numSeatsToHold, email);
	}

	/**
	 * Call findAndHoldSeats for the service. Hardcoded email.
	 * 
	 * Returns SeatHold object.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param numSeatsToHold
	 *            number of seats to hold
	 * @return SeatHold
	 */
	private static SeatHold holdSeats(TicketService service, int numSeatsToHold) {
		return holdSeats(service, numSeatsToHold, "fake@fake.com");
	}

	/**
	 * Call reserveSeats for the service.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param id
	 *            SeatHold Id
	 * @param email
	 *            email address
	 * @return Confirmation code
	 */
	private static String reserveSeats(TicketService service, int id, String email) {
		return service.reserveSeats(id, email);
	}

	/**
	 * Call reserveSeats for the service. Hardcoded email.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param id
	 *            SeatHold Id
	 * @param email
	 *            email address
	 * @return Confirmation code
	 */
	private static String reserveSeats(TicketService service, int id) {
		return reserveSeats(service, id, "fake@fake.com");
	}

	/**
	 * Call reserveSeats for the service. Automatically holds the seats first.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param numSeatsToHold
	 *            number of seats to hold
	 * @param email
	 *            email address
	 * @return Confirmation code
	 */
	private static String holdAndReserveSeats(TicketService service, int numSeatsToHold, String email) {
		SeatHold sh = holdSeats(service, numSeatsToHold, email);
		return reserveSeats(service, sh.getId(), email);
	}

	/**
	 * Call reserveSeats for the service. Automatically holds the seats first.
	 * Hardcoded email.
	 * 
	 * @param service
	 *            TicketService to use
	 * @param numSeatsToHold
	 *            number of seats to hold
	 * @param email
	 *            email address
	 * @return Confirmation code
	 */
	private static String holdAndReserveSeats(TicketService service, int numSeatsToHold) {
		return holdAndReserveSeats(service, numSeatsToHold, "fake@fake.com");
	}

	@Test
	public void _testTearDown() {
		// covers the null checks in tearDown

		serviceShortHold = null;
		serviceDefaultHold = null;
		serviceLongHold = null;
		serviceVeryLongHold = null;
	}

	@Test
	public void testNumSeatsAvailableAllAvailable() {
		// test that not reserving anything returns the correct amount

		TicketService service = serviceLongHold;

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats();
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFindAndHoldSeatsSome() {
		// test that holding some seats returns the correct amount

		TicketService service = serviceLongHold;

		int seatsToHold = service.getTicketmaster().getVenue().getColCount() - 1;
		holdSeats(service, seatsToHold);

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFindAndHoldSeatsAll() {
		// test that holding all seats returns the correct amount

		TicketService service = serviceVeryLongHold;

		int seatsToHold = 1;
		while (service.numSeatsAvailable() > 0) {
			holdSeats(service, seatsToHold);
		}

		int expected = 0;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFindAndHoldSeatNone() {
		// shouldn't be able to no or negative seats

		TicketService service = serviceLongHold;

		SeatHold sh;

		sh = holdSeats(service, 0);
		Assert.assertNull(sh);

		sh = holdSeats(service, -1);
		Assert.assertNull(sh);
	}

	@Test
	public void testFindAndHoldSeatTooLarge() {
		// shouldn't be able to reserve more seats than the venue

		TicketService service = serviceLongHold;

		int seatsToHold = service.getTicketmaster().getVenue().getNumberOfSeats() + 1;
		SeatHold sh = holdSeats(service, seatsToHold);

		Assert.assertNull(sh);
	}

	@Test
	public void testFindAndHoldSeatsMaxedOut() {
		// when all seats are reserved, shouldn't be able to get a seat

		TicketService service = serviceVeryLongHold;

		int seatsToHold = 1;
		while (service.numSeatsAvailable() > 0) {
			holdSeats(service, seatsToHold);
		}

		SeatHold sh = holdSeats(service, 1);

		Assert.assertNull(sh);
	}

	@Test
	public void testFindAndHoldSeatsExpire() throws InterruptedException {
		// test that holding seats will expire

		TicketService service = serviceShortHold;

		int seatsToHold = 1;
		holdSeats(service, seatsToHold);

		Thread.sleep(2 * service.getTicketmaster().getHoldTimeoutSeconds() * 1000);

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats();
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeats() {
		// make sure seats can be reserved
		// and seats are unique

		TicketService service = serviceLongHold;

		int seatsToHold = service.getTicketmaster().getVenue().getColCount() - 1;
		SeatHold sh = holdSeats(service, seatsToHold);
		reserveSeats(service, sh.getId());

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);

		Set<Seat> set = new HashSet<Seat>(Arrays.asList(sh.getSeats()));
		Assert.assertEquals(sh.getSeats().length, set.size());
	}

	@Test
	public void testReserveSeatsDontExpire() throws InterruptedException {
		// make sure seats can be reserved

		TicketService service = serviceShortHold;

		int seatsToHold = service.getTicketmaster().getVenue().getColCount() - 1;
		holdAndReserveSeats(service, seatsToHold);

		// wait long enough for the hold to expire
		Thread.sleep(2 * service.getTicketmaster().getHoldTimeoutSeconds() * 1000);

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeatsInvalidEmail() {
		// can't reserve with an invalid email

		TicketService service = serviceShortHold;

		int seatsToHold = service.getTicketmaster().getVenue().getColCount() - 1;
		String email = "fake@fake.com";
		String emailInvalid = "invalid@fake.com";
		SeatHold sh = holdSeats(service, seatsToHold, email);
		String confirmationCode = reserveSeats(service, sh.getId(), emailInvalid);

		Assert.assertNull(confirmationCode);
	}

	@Test
	public void testReserveSeatsInvalidId() {
		// can't reserve with an invalid id

		TicketService service = serviceDefaultHold;

		String confirmationCode = reserveSeats(service, 1);

		Assert.assertNull(confirmationCode);
	}

	private void testReserveSeatsRandomFill() {
		// randomly fill all seats

		TicketService service = serviceLongHold;

		Random r = new Random();
		int availableSeats = service.numSeatsAvailable();
		while (availableSeats > 0) {
			int seatsToHold = r.nextInt(availableSeats) + 1;
			holdAndReserveSeats(service, seatsToHold);
			availableSeats = service.numSeatsAvailable();
		}

		int expected = 0;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeatsRandomFill1() {
		testReserveSeatsRandomFill();
	}

	@Test
	public void testReserveSeatsRandomFill2() {
		testReserveSeatsRandomFill();
	}

	@Test
	public void testReserveSeatsRandomFill3() {
		testReserveSeatsRandomFill();
	}

	private static int holdAndReserveAsMuchAsPossible(TicketService service, int seatsToHoldIncrement) {
		int seatsHeld = 0;
		while (service.numSeatsAvailable() >= seatsToHoldIncrement) {
			holdAndReserveSeats(service, seatsToHoldIncrement);
			seatsHeld += seatsToHoldIncrement;
		}

		return seatsHeld;
	}

	private static void holdAndReserveAsMuchAsPossibleTest(TicketService service, int seatsToHoldIncrement) {
		int seatsHeld = holdAndReserveAsMuchAsPossible(service, seatsToHoldIncrement);

		int numSeatsAvailable = service.numSeatsAvailable();
		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsHeld;
		int actual = numSeatsAvailable;
		Assert.assertEquals(expected, actual);
		Assert.assertTrue(numSeatsAvailable < seatsToHoldIncrement);
	}

	@Test
	public void testReserveSeatsAll() {
		// make sure all seats can be reserved
		// also tests alternating holding and reserving

		TicketService service = serviceLongHold;

		int seatsToHold = 1;
		holdAndReserveAsMuchAsPossible(service, seatsToHold);

		int expected = 0;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeatsLargerThanColSize() {
		// make sure seats can be reserved when requesting more than one
		// rows worth of seats

		TicketService service = serviceLongHold;

		int seatsToHold = service.getTicketmaster().getVenue().getColCount() + 1;
		holdAndReserveAsMuchAsPossibleTest(service, seatsToHold);
	}

	@Test
	public void testReserveSeatsHalfCol() {
		// make sure seats can be reserved half of the row at a time
		// using a number larger than half to force seats to eventually split

		TicketService service = serviceLongHold;

		int seatsToHold = (service.getTicketmaster().getVenue().getColCount() / 2) + 1;
		holdAndReserveAsMuchAsPossibleTest(service, seatsToHold);
	}

}
