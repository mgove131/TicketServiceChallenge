package test.java.ui.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import main.java.ui.util.GridPaneUtil;

public final class GridPaneUtilTest {

	private GridPane gridPane;

	@Before
	public void setUp() throws Exception {
		gridPane = new GridPane();
	}

	@Test
	public void testAddColumnAndRowConstraints() {
		// test that rows and columns are added

		Priority[] columnPriorites = new Priority[] { Priority.ALWAYS, Priority.NEVER, Priority.SOMETIMES };
		Priority[] rowPriorites = new Priority[] { Priority.ALWAYS, Priority.NEVER, Priority.SOMETIMES };
		GridPaneUtil.addColumnAndRowConstraints(gridPane, columnPriorites, rowPriorites);

		Assert.assertEquals(columnPriorites.length, gridPane.getColumnConstraints().size());
		Assert.assertEquals(rowPriorites.length, gridPane.getRowConstraints().size());
	}

	@Test
	public void testAddColumnAndRowConstraintsNull() {
		// test null checks

		Priority[] columnPriorites = new Priority[] { Priority.ALWAYS, Priority.NEVER, Priority.SOMETIMES };
		Priority[] rowPriorites = new Priority[] { Priority.ALWAYS, Priority.NEVER, Priority.SOMETIMES };
		GridPaneUtil.addColumnAndRowConstraints(gridPane, null, null);
		GridPaneUtil.addColumnAndRowConstraints(null, columnPriorites, rowPriorites);
		GridPaneUtil.addColumnAndRowConstraints(null, null, null);

		Assert.assertEquals(0, gridPane.getColumnConstraints().size());
		Assert.assertEquals(0, gridPane.getRowConstraints().size());
	}

}
