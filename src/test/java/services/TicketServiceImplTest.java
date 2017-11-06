package test.java.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

	@Test
	public void testTearDownInternal() {
		// covers the null checks in tearDown

		serviceShortHold = null;
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

		int seatsToHold = 3;
		String email = "fake@fake.com";
		service.findAndHoldSeats(seatsToHold, email);

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFindAndHoldSeatsAll() {
		// test that holding all seats returns the correct amount

		TicketService service = serviceVeryLongHold;

		String email = "fake@fake.com";
		while (service.numSeatsAvailable() > 0) {
			service.findAndHoldSeats(1, email);
		}

		int expected = 0;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFindAndHoldSeatsExpire() {
		// test that holding seats will expire

		TicketService service = serviceShortHold;

		int seatsToHold = 3;
		String email = "fake@fake.com";
		service.findAndHoldSeats(seatsToHold, email);

		try {
			// wait long enough for the hold to expire
			Thread.sleep(2 * service.getTicketmaster().getHoldTimeoutSeconds() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats();
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeats() {
		// make sure seats can be reserved

		TicketService service = serviceLongHold;

		int seatsToHold = 3;
		String email = "fake@fake.com";
		SeatHold sh = service.findAndHoldSeats(seatsToHold, email);
		service.reserveSeats(sh.getId(), email);

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReserveSeatsDontExpire() {
		// make sure seats can be reserved

		TicketService service = serviceShortHold;

		int seatsToHold = 3;
		String email = "fake@fake.com";
		SeatHold sh = service.findAndHoldSeats(seatsToHold, email);
		service.reserveSeats(sh.getId(), email);

		try {
			// wait long enough for the hold to expire
			Thread.sleep(2 * service.getTicketmaster().getHoldTimeoutSeconds() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int expected = service.getTicketmaster().getVenue().getNumberOfSeats() - seatsToHold;
		int actual = service.numSeatsAvailable();
		Assert.assertEquals(expected, actual);
	}
}
