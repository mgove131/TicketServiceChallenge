package main.java.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ui.view.MainPaneView;
import main.java.ui.viewmodel.MainPaneViewModel;

/**
 * Main UI class. Run this.
 * 
 * @author User
 *
 */
public final class TicketServiceUI extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainPaneViewModel mainVM = new MainPaneViewModel();
		MainPaneView mainV = new MainPaneView(mainVM);

		Scene scene = new Scene(mainV.getRoot(), 600, 300);
		primaryStage.setTitle("TicketServiceUI");
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
