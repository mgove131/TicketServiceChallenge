package main.java.ui.viewmodel;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.logic.Ticketmaster;
import main.java.model.SeatHold;

/**
 * ViewModel for MainPaneView.
 * 
 * @author User
 *
 */
public final class MainPaneViewModel implements Closeable {

	/**
	 * Constructor.
	 */
	public MainPaneViewModel() {
		this.ticketMaster = new Ticketmaster();
		this.output = new SimpleStringProperty();
		this.seatsInput = new SimpleStringProperty();
		this.emailInput = new SimpleStringProperty();
		this.seatsHeld = FXCollections.observableArrayList(new ArrayList<SeatHold>());
		this.seatsReserved = FXCollections.observableArrayList(new ArrayList<SeatHold>());
	}

	@Override
	public void close() throws IOException {
		this.ticketMaster.close();

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

	private ObservableList<SeatHold> seatsHeld;

	public ObservableList<SeatHold> getSeatsHeld() {
		return seatsHeld;
	}

	private ObservableList<SeatHold> seatsReserved;

	public ObservableList<SeatHold> getSeatsReserved() {
		return seatsReserved;
	}

	private SeatHold currentSeatHold;

	private SeatHold getCurrentSeatHold() {
		return currentSeatHold;
	}

	private void setCurrentSeatHold(SeatHold sh) {
		currentSeatHold = sh;
		setOutput(String.format("%s", currentSeatHold));
	}

	private void updateSeatInfo() {
		seatsHeld.clear();
		seatsHeld.addAll(ticketMaster.getSeatHolds());
		seatsReserved.clear();
		seatsReserved.addAll(ticketMaster.getSeatReservations());
	}

	/**
	 * Call this to hold the seats from the input.
	 */
	public void holdSeats() {
		try {
			int numSeats = Integer.parseInt(getSeatsInput());
			String customerEmail = getEmailInput();

			int seatsAvailable = ticketMaster.numSeatsAvailable();
			SeatHold sh = ticketMaster.findAndHoldSeats(numSeats, customerEmail);
			if (sh == null) {
				setOutput(String.format("Could not hold seats. Seats Available: %s", seatsAvailable));
			} else {
				setCurrentSeatHold(sh);
			}
		} catch (NumberFormatException e) {
			setOutput(String.format("Cannot parse number of seats, expecting integer value: %s", getSeatsInput()));
		}

		updateSeatInfo();
	}

	/**
	 * Call this to reserve the existing held seats.
	 */
	public void reserveSeats() {
		SeatHold currentSeat = getCurrentSeatHold();

		if (currentSeat == null) {
			setOutput(String.format("Cannot reserve seats: No seats being held."));
		} else if (!getEmailInput().equals(currentSeat.getEmail())) {
			setOutput(String.format("Cannot reserve seats: Email does not match."));
		} else {
			int seatHoldId = currentSeat.getId();
			String customerEmail = getEmailInput();
			String confirmationCode = ticketMaster.reserveSeats(seatHoldId, customerEmail);

			if (confirmationCode == null) {
				setOutput(String.format("Could not reserve seats."));
			} else {
				setOutput(String.format("Confirmation code: %s", confirmationCode));
			}
		}

		updateSeatInfo();
	}
}
