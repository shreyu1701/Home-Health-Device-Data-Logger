package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class UpdateHealthDataController {

    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtBloodPressure;
    @FXML
    private TextField txtSugarLevel;
    @FXML
    private TextField txtHeartRate;
    @FXML
    private TextField txtOxygenLevel;

    @FXML
    private Button btnCancel;

    private PatientHealthData selectedHealthData;
    private boolean isUpdated = false;

    public void initialize() {
        // Optional initialization logic (if needed)
    }

    public void setInitialData(PatientHealthData selectedHealthData, User user) {
        this.selectedHealthData = selectedHealthData;
        selectedHealthData.setUserId(selectedHealthData.getUserId());
        txtDate.setText(selectedHealthData.getDate().toString());
        txtBloodPressure.setText(selectedHealthData.getBloodPressure());
        txtSugarLevel.setText(selectedHealthData.getSugarLevel());
        txtHeartRate.setText(String.valueOf(selectedHealthData.getHeartRate()));
        txtOxygenLevel.setText(String.valueOf(selectedHealthData.getOxygenLevel()));

    }
    public void showUpdateDialog(PatientHealthData healthData) {
        this.selectedHealthData = healthData;

        // Pre-fill fields with the selected record's data
        txtDate.setText(selectedHealthData.getDate().toString());
        txtBloodPressure.setText(selectedHealthData.getBloodPressure());
        txtSugarLevel.setText(selectedHealthData.getSugarLevel());
        txtHeartRate.setText(String.valueOf(selectedHealthData.getHeartRate()));
        txtOxygenLevel.setText(String.valueOf(selectedHealthData.getOxygenLevel()));
        // Show the dialog and wait for user action
//        Stage stage = (Stage) txtDate.getScene().getWindow();
//        stage.showAndWait();
//
//        return isUpdated; Stage stage = (Stage) txtDate.getScene().getWindow();
//        stage.showAndWait();
//
//        return isUpdated;
    }

    @FXML
    private void handleSave() {
        String date = txtDate.getText();
        String bloodPressure = txtBloodPressure.getText();
        String sugarLevel = txtSugarLevel.getText();
        int heartRate;
        int oxygenLevel;

        try {
            heartRate = Integer.parseInt(txtHeartRate.getText());
            oxygenLevel = Integer.parseInt(txtOxygenLevel.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Heart Rate and Oxygen Level must be numeric.");
            return;
        }

        // Update the database

        String updateQuery = "UPDATE HealthData SET data_date = TO_DATE(?, 'YYYY-MM-DD'), " +
                "blood_pressure = ?, sugar_level = ?, heart_rate = ?, oxygen_level = ? " +
                "WHERE user_id = ? AND data_date = TO_DATE(?, 'YYYY-MM-DD')";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, bloodPressure);
            preparedStatement.setString(3, sugarLevel);
            preparedStatement.setInt(4, heartRate);
            preparedStatement.setInt(5, oxygenLevel);
            preparedStatement.setString(6, selectedHealthData.getUserId());
            preparedStatement.setString(7, selectedHealthData.getDate().toString());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Record updated successfully.");
                closeDialog();
            } else {
                showAlert("Error", "Failed to update the record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }

        
    }

    private void closeDialog() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the dialog without saving changes
        isUpdated = false;
        Stage stage = (Stage) txtDate.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


