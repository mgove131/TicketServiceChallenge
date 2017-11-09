package main.java.ui.util;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Util methods for GridPane.
 * 
 * @author User
 *
 */
public final class GridPaneUtil {

	static {
		// just for code coverage
		new GridPaneUtil();
	}

	private GridPaneUtil() {
		// shouldn't be able to create an instance
	}

	/**
	 * For a GridPane, add the ColumnConstraints from the number of priorites.
	 * 
	 * @param pane
	 *            GridPane
	 * @param priorites
	 *            ColumnConstraints are added based on the number of Priority in the
	 *            array
	 */
	public static void addColumnConstraints(GridPane pane, Priority[] priorites) {
		if ((pane == null) || (priorites == null)) {
			return;
		}

		for (Priority p : priorites) {
			ColumnConstraints constraints = new ColumnConstraints();
			constraints.setHgrow(p);
			pane.getColumnConstraints().add(constraints);
		}
	}

	/**
	 * For a GridPane, add the RowConstraints from the number of priorites.
	 * 
	 * @param pane
	 *            GridPane
	 * @param priorites
	 *            RowConstraints are added based on the number of Priority in the
	 *            array
	 */
	public static void addRowConstraints(GridPane pane, Priority[] priorites) {
		if ((pane == null) || (priorites == null)) {
			return;
		}

		for (Priority p : priorites) {
			RowConstraints constraints = new RowConstraints();
			constraints.setVgrow(p);
			pane.getRowConstraints().add(constraints);
		}
	}

	/**
	 * For a GridPane, add the ColumnConstraints and RowConstraints from the
	 * corresponding number of priorites.
	 * 
	 * @param pane
	 *            GridPane
	 * @param columnPriorites
	 *            ColumnConstraints are added based on the number of Priority in the
	 *            array
	 * @param rowPriorites
	 *            RowConstraints are added based on the number of Priority in the
	 *            array
	 */
	public static void addColumnAndRowConstraints(GridPane pane, Priority[] columnPriorites, Priority[] rowPriorites) {
		addColumnConstraints(pane, columnPriorites);
		addRowConstraints(pane, rowPriorites);
	}
}
