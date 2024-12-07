package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthDataManager;


import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddHealthDataController {

	@FXML
	private Button btnSaveHealthData;
	@FXML
	private Button btnCancelHealthData;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField bloodPressureField;
	@FXML
	private TextField sugarLevelField;
	@FXML
	private TextField heartRateField;
	@FXML
	private TextField oxygenLevelField;
	@FXML
	private TextArea commentsArea;

	private User currentUser;

	@FXML
	public void initialize() {
		// Set up tooltips for user guidance
		bloodPressureField.setTooltip(new Tooltip("Enter Blood Pressure in mmHg (e.g., 120)"));
		sugarLevelField.setTooltip(new Tooltip("Enter Sugar Level in mg/dL (e.g., 100)"));
		heartRateField.setTooltip(new Tooltip("Enter Heart Rate in bpm (e.g., 70)"));
		oxygenLevelField.setTooltip(new Tooltip("Enter Oxygen Level in % (e.g., 98)"));

		// Set default date to today
		datePicker.setValue(LocalDate.now());
	}

	@FXML
	public void saveHealthData(ActionEvent actionEvent) throws IOException, SQLException {
		if (validateInputs()) {
			User currentUser = UserSession.getInstance().getCurrentUser();

			// Retrieve date (mandatory field)
			LocalDate date = datePicker.getValue();

			// Retrieve optional fields, using null if blank
			String bp = bloodPressureField.getText().isEmpty() ? null : bloodPressureField.getText();
			String sugar = sugarLevelField.getText().isEmpty() ? null : sugarLevelField.getText();
			Integer heart = heartRateField.getText().isEmpty() ? 0 : Integer.parseInt(heartRateField.getText());
			Integer oxygenLevel = oxygenLevelField.getText().isEmpty() ? 0 : Integer.parseInt(oxygenLevelField.getText());
			String comment = commentsArea.getText().isEmpty() ? null : commentsArea.getText();

			LocalDateTime creationDateTime = LocalDateTime.now();

			if (currentUser != null) {
				PatientHealthData healthData = new PatientHealthData(date, bp, sugar, heart, oxygenLevel, comment, creationDateTime);
				PatientHealthDataManager manager = new PatientHealthDataManager();
				manager.addHealthData(healthData, currentUser);
			}

			showAlert(AlertType.INFORMATION, "Success", "Data saved successfully!");
			clearForm();
		}
	}


	public void cancelHealthData(ActionEvent actionEvent) throws IOException {
		Stage stage = (Stage) btnCancelHealthData.getScene().getWindow();
		stage.close();

//		URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/PatientDashboard.fxml");
//		GUILoader(fxmlLocation, btnCancelHealthData);
	}

	private boolean validateInputs() {
		StringBuilder errorMessage = new StringBuilder();
		boolean isValid = true;

		// Validate Date
		LocalDate selectedDate = datePicker.getValue();
		if (selectedDate == null || selectedDate.isAfter(LocalDate.now())) {
			errorMessage.append("Date must be today or earlier.\n");
			isValid = false;
			datePicker.requestFocus();
		}

		// Validation for optional fields should only occur if the fields are not empty
		if (!bloodPressureField.getText().isEmpty() && !isValidBloodPressure(bloodPressureField.getText())) {
			errorMessage.append("Blood Pressure must be in the format systolic/diastolic (e.g., 120/80) and within realistic ranges.\n");
			isValid = false;
		}

		if (!sugarLevelField.getText().isEmpty() && !isValidSugarLevel(sugarLevelField.getText())) {
			errorMessage.append("Sugar Level must be a number between 70 and 200 mg/dL.\n");
			isValid = false;
		}

		if (!heartRateField.getText().isEmpty() && !isValidHeartRate(heartRateField.getText())) {
			errorMessage.append("Heart Rate must be a number between 40 and 180 bpm.\n");
			isValid = false;
		}

		if (!oxygenLevelField.getText().isEmpty() && !isValidOxygenLevel(oxygenLevelField.getText())) {
			errorMessage.append("Oxygen Level must be a percentage between 80 and 100%.\n");
			isValid = false;
		}

		// Show error messages if any validation failed
		if (!isValid) {
			showAlert(AlertType.ERROR, "Validation Error", errorMessage.toString());
		}

		return isValid;
	}


	private boolean isValidBloodPressure(String bloodPressure) {
		String regex = "^\\d{2,3}/\\d{2,3}$";
		if (!bloodPressure.matches(regex)) {
			return false;
		}
		try {
			String[] parts = bloodPressure.split("/");
			int systolic = Integer.parseInt(parts[0]);
			int diastolic = Integer.parseInt(parts[1]);

			// Validate systolic and diastolic ranges
			return systolic >= 90 && systolic <= 180 && diastolic >= 60 && diastolic <= 120;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidSugarLevel(String sugarLevel) {
		return isValidInteger(sugarLevel, 70, 200);
	}

	private boolean isValidHeartRate(String heartRate) {
		return isValidInteger(heartRate, 40, 180);
	}

	private boolean isValidOxygenLevel(String oxygenLevel) {
		return isValidInteger(oxygenLevel, 80, 100);
	}

	private boolean isValidInteger(String text, int minValue, int maxValue) {
		try {
			int value = Integer.parseInt(text);
			return value >= minValue && value <= maxValue;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void clearForm() {
		// Reset all text fields
		bloodPressureField.clear();
		sugarLevelField.clear();
		heartRateField.clear();
		oxygenLevelField.clear();
		commentsArea.clear();
	}


	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
