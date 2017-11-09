package test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.model.SeatHoldTest;
import test.java.model.SeatTest;
import test.java.services.TicketServiceImplTest;
import test.java.ui.util.GridPaneUtilTest;
import test.java.ui.view.MainPaneViewTest;
import test.java.ui.viewmodel.MainPaneViewModelTest;

@RunWith(Suite.class)
@SuiteClasses({ SeatHoldTest.class, SeatTest.class, TicketServiceImplTest.class, GridPaneUtilTest.class,
		MainPaneViewTest.class, MainPaneViewModelTest.class })
public final class AllTests {
	@BeforeClass
	public static void setUpBeforeClass() {
		// only for code coverage
		new AllTests();
	}
}