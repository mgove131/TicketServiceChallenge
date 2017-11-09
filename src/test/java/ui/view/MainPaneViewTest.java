package test.java.ui.view;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import main.java.ui.view.MainPaneView;
import main.java.ui.viewmodel.MainPaneViewModel;

public class MainPaneViewTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// load platform
		new JFXPanel();
	}

	@Test
	public void test() throws IOException {

		MainPaneView view = new MainPaneView(new MainPaneViewModel());
		view.getViewModel().close();
		Assert.assertNotNull(view);
	}
}
