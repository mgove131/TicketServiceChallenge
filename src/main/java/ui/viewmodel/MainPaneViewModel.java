package main.java.ui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.logic.Ticketmaster;
import main.java.model.SeatHold;

/**
 * ViewModel for MainPaneView.
 * 
 * @author User
 *
 */
public final class MainPaneViewModel {

	/**
	 * Constructor.
	 */
	public MainPaneViewModel() {
		this.ticketMaster = new Ticketmaster();
		this.output = new SimpleStringProperty();
		this.seatsInput = new SimpleStringProperty();
		this.emailInput = new SimpleStringProperty();
	}

	private Ticketmaster ticketMaster;

	StringProperty output;

	public final String getOutput() {
		return output.get();
	}

	public final void setOutput(String value) {
		output.set(value);
	}

	public StringProperty outputProperty() {
		return output;
	}

	StringProperty seatsInput;

	public final String getSeatsInput() {
		return seatsInput.get();
	}

	public final void setSeatsInput(String value) {
		seatsInput.set(value);
	}

	public StringProperty seatsInputProperty() {
		return seatsInput;
	}

	StringProperty emailInput;

	public final String getEmailInput() {
		return emailInput.get();
	}

	public final void setEmailInput(String value) {
		emailInput.set(value);
	}

	public StringProperty emailInputProperty() {
		return emailInput;
	}

	private SeatHold currentSeatHold;

	private void setCurrentSeatHold(SeatHold sh) {
		currentSeatHold = sh;
		setOutput(String.format("%s", currentSeatHold));
	}

	/**
	 * Call this to hold the seats from the input.
	 */
	public void holdSeats() {
		try {
			int numSeats = Integer.parseInt(getSeatsInput());
			String customerEmail = getEmailInput();

			SeatHold sh = ticketMaster.findAndHoldSeats(numSeats, customerEmail);
			if (sh == null) {
				setOutput(String.format("Could not hold seats."));
			} else {
				setCurrentSeatHold(sh);
			}
		} catch (NumberFormatException e) {
			setOutput(String.format("Cannot parse number of seats, expecting integer value: %s", getSeatsInput()));
		}

	}
}
