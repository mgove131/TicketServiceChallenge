package main.java.ui.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import main.java.ui.util.GridPaneUtil;
import main.java.ui.viewmodel.MainPaneViewModel;

/**
 * Main pane of the UI.
 * 
 * @author User
 *
 */
public final class MainPaneView {

	/**
	 * Constructor.
	 * 
	 * @param viewModel
	 *            ViewModel.
	 */
	public MainPaneView(MainPaneViewModel viewModel) {
		this.viewModel = viewModel;

		Label output = new Label();
		output.textProperty().bind(this.viewModel.outputProperty());
		HBox outputPane = new HBox(output);
		HBox.setHgrow(output, Priority.ALWAYS);

		Label seatsLabel = new Label("Seats (hold): ");
		seatsLabel.setAlignment(Pos.CENTER_RIGHT);
		TextField seatsInput = new TextField();
		this.viewModel.seatsInputProperty().bind(seatsInput.textProperty());
		Label emailLabel = new Label("Email (hold/reserve): ");
		emailLabel.setAlignment(Pos.CENTER_RIGHT);
		TextField emailInput = new TextField();
		this.viewModel.emailInputProperty().bind(emailInput.textProperty());
		HBox inputPane = new HBox(seatsLabel, seatsInput, emailLabel, emailInput);
		HBox.setHgrow(seatsLabel, Priority.NEVER);
		HBox.setHgrow(seatsInput, Priority.ALWAYS);
		HBox.setHgrow(seatsLabel, Priority.NEVER);
		HBox.setHgrow(seatsInput, Priority.ALWAYS);

		Button holdSeatsButton = new Button("Hold Seats");
		holdSeatsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (viewModel != null) {
					viewModel.holdSeats();
				}
			}
		});
		Button reserveSeatsButton = new Button("Reserve Seats");
		reserveSeatsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (viewModel != null) {
					viewModel.holdSeats();
				}
			}
		});
		HBox buttonsPane = new HBox(holdSeatsButton, reserveSeatsButton);

		HBox.setHgrow(holdSeatsButton, Priority.NEVER);
		HBox.setHgrow(reserveSeatsButton, Priority.NEVER);

		GridPane root = new GridPane();
		GridPaneUtil.addColumnAndRowConstraints(root, new Priority[] { Priority.SOMETIMES },
				new Priority[] { Priority.ALWAYS, Priority.NEVER, Priority.NEVER, Priority.NEVER });

		root.add(outputPane, 0, 1);
		root.add(inputPane, 0, 2);
		root.add(buttonsPane, 0, 3);

		this.root = root;
	}

	private MainPaneViewModel viewModel;

	private Pane root;

	public Pane getRoot() {
		return root;
	}

	private Label output;
}