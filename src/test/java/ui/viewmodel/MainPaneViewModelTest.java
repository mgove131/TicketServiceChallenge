package test.java.ui.viewmodel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.java.logic.Factory;
import main.java.model.SeatHold;
import main.java.model.Venue;
import main.java.ui.viewmodel.MainPaneViewModel;

public final class MainPaneViewModelTest {

	MainPaneViewModel vm;

	@Before
	public void setUp() throws Exception {
		vm = new MainPaneViewModel();
	}

	@After
	public void tearDown() throws Exception {
		vm.close();
	}

	@Test
	public void testHoldSeats() {
		// test holding a seat

		String seatInput = "1";
		String email = "fake@fake.com";
		vm.setSeatsInput(seatInput);
		vm.setEmailInput(email);
		vm.holdSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNotNull(sh);
		Assert.assertEquals(1, vm.getSeatsHeld().size());
		Assert.assertEquals(seatInput, String.valueOf(sh.getSeats().length));
		Assert.assertEquals(email, sh.getEmail());
	}

	@Test
	public void testHoldSeatsInvalid() {
		// test invalid seat input

		String seatInput = "abc";
		vm.setSeatsInput(seatInput);
		vm.holdSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNull(sh);
	}

	@Test
	public void testHoldSeatsTooManySeats() {
		// requesting too many seats

		Venue v = Factory.getVenue();

		String seatInput = String.valueOf(v.getNumberOfSeats() + 1);
		vm.setSeatsInput(seatInput);
		vm.holdSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNull(sh);
	}

	@Test
	public void testReserveSeats() {
		// test reserving a seat

		String seatInput = "1";
		String email = "fake@fake.com";
		vm.setSeatsInput(seatInput);
		vm.setEmailInput(email);
		vm.holdSeats();
		vm.reserveSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNotNull(sh);
		Assert.assertEquals(0, vm.getSeatsHeld().size());
		Assert.assertEquals(1, vm.getSeatsReserved().size());
	}

	@Test
	public void testReserveSeatsWrongEmail() {
		// test reserving a seat

		String seatInput = "1";
		String email = "fake@fake.com";
		String emailWrong = "wrong@fake.com";
		vm.setSeatsInput(seatInput);
		vm.setEmailInput(email);
		vm.holdSeats();
		vm.setEmailInput(emailWrong);
		vm.reserveSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNotNull(sh);
		Assert.assertEquals(1, vm.getSeatsHeld().size());
		Assert.assertEquals(0, vm.getSeatsReserved().size());
	}

	@Test
	public void testReserveSeatsNoHold() {
		// test trying to reserving a seat with no hold

		vm.reserveSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNull(sh);
		Assert.assertEquals(0, vm.getSeatsHeld().size());
		Assert.assertEquals(0, vm.getSeatsReserved().size());
	}

	@Test
	public void testReserveSeatsExpiredHold() throws InterruptedException {
		// test trying to reserve a seat with the hold expired

		String seatInput = "1";
		String email = "fake@fake.com";
		vm.setSeatsInput(seatInput);
		vm.setEmailInput(email);
		vm.holdSeats();
		Thread.sleep((5 + 1) * 1000); // 5 is current default, wait a little longer
		vm.reserveSeats();

		SeatHold sh = vm.getCurrentSeatHold();
		Assert.assertNotNull(sh);
		Assert.assertEquals(0, vm.getSeatsHeld().size());
		Assert.assertEquals(0, vm.getSeatsReserved().size());
	}
}
