package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthDataManager;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;

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

	public void saveHealthData(ActionEvent actionEvent) throws IOException {
		if (validateInputs()) {

			// If all inputs are valid, proceed with saving data
			LocalDate date = datePicker.getValue();
			String bp = bloodPressureField.getText();
			String sugar = sugarLevelField.getText();
			String heart = heartRateField.getText();
			String oxygen = oxygenLevelField.getText();
			String comment = commentsArea.getText();

			PatientHealthData healthData = new PatientHealthData(date, bp, sugar, heart, oxygen, comment);
			PatientHealthDataManager Manager = new PatientHealthDataManager();

			Manager.addHealthData(healthData);

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

		// Validate Blood Pressure (format: systolic/diastolic, e.g., 120/80)
		if (!isValidBloodPressure(bloodPressureField.getText())) {
			errorMessage.append("Blood Pressure must be in the format systolic/diastolic (e.g., 120/80) and within realistic ranges.\n");
			isValid = false;
			bloodPressureField.requestFocus();
		}

		// Validate Sugar Level
		if (!isValidSugarLevel(sugarLevelField.getText())) {
			errorMessage.append("Sugar Level must be a number between 70 and 200 mg/dL.\n");
			isValid = false;
			sugarLevelField.requestFocus();
		}

		// Validate Heart Rate
		if (!isValidHeartRate(heartRateField.getText())) {
			errorMessage.append("Heart Rate must be a number between 40 and 180 bpm.\n");
			isValid = false;
			heartRateField.requestFocus();
		}

		// Validate Oxygen Level
		if (!isValidOxygenLevel(oxygenLevelField.getText())) {
			errorMessage.append("Oxygen Level must be a percentage between 80 and 100%.\n");
			isValid = false;
			oxygenLevelField.requestFocus();
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
