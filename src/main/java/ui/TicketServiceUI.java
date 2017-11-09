package main.java.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.ui.view.MainPaneView;
import main.java.ui.viewmodel.MainPaneViewModel;

/**
 * Main UI class. Run this.
 * 
 * @author User
 *
 */
public final class TicketServiceUI extends Application {

	private MainPaneView mainV;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.mainV = new MainPaneView(new MainPaneViewModel());

		Scene scene = new Scene(mainV.getRoot(), 600, 300);
		primaryStage.setTitle("TicketServiceUI");
		primaryStage.setOnCloseRequest((e) -> {
			try {
				this.mainV.getViewModel().close();
			} catch (Exception e1) {
			}

			Platform.exit();
			System.exit(0);
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Request the application to close.
	 */
	public void close() {
		primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public static void main(String args[]) {
		launch(args);
	}
}
