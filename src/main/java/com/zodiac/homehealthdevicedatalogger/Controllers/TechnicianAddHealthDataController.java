package com.zodiac.homehealthdevicedatalogger.Controllers;


import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TechnicianAddHealthDataController {

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
    private DatePicker datePicker;
    @FXML
    private TextField userIdField;
    @FXML
    private Button cancelHealthData;


   DBConnect dbConnect = new DBConnect();




    @FXML
    private void saveData() {
        // Get data from fields
        String userId = userIdField.getText();
        String bloodPressure = bloodPressureField.getText();
        String sugarLevel = sugarLevelField.getText();
        String heartRate = heartRateField.getText();
        String oxygenLevel = oxygenLevelField.getText();
        String comments = commentsArea.getText();
        LocalDate date = datePicker.getValue();
        LocalDateTime creationDateTime = LocalDateTime.now();

        if (userId.isEmpty() || date == null) {
            showAlert(Alert.AlertType.INFORMATION,"Error", "Please fill in all required fields marked with *.");
            return;
        }

        // Insert data into the database
        String insertQuery = "INSERT INTO HealthData (USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS, CREATIONDATETIME) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setString(3, bloodPressure);
            preparedStatement.setString(4, sugarLevel);
            preparedStatement.setString(5, heartRate);
            preparedStatement.setString(6, oxygenLevel);
            preparedStatement.setString(7, comments);
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(creationDateTime));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Data saved successfully!");
                clearFields();
            } else {
                showAlert(Alert.AlertType.INFORMATION,"Error", "Failed to save health data. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.INFORMATION,"Error", "An error occurred while saving data: " + e.getMessage());
        }
    }

    @FXML
    private void cancelAction() {
        clearFields();
        cancelHealthData.setOnAction(event -> closeDialog());
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelHealthData.getScene().getWindow();
        stage.close();
    }


    private void clearFields() {
        userIdField.clear();
        bloodPressureField.clear();
        sugarLevelField.clear();
        heartRateField.clear();
        oxygenLevelField.clear();
        commentsArea.clear();
        datePicker.setValue(LocalDate.now());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
