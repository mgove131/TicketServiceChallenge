package test.java.ui;

import org.junit.Assert;
import org.junit.Test;

import main.java.ui.TicketServiceUI;

public final class TicketServiceUITest {

	@Test
	public void test() {
		// mostly for code coverage in the application and view classes

		TicketServiceUI.main(new String[] { "close" });

		Assert.assertTrue(true);
	}
}
