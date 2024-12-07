package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePatientController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField bloodGroupField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private PatientHealthData currentPatientData;

    DBConnect dbConnect = new DBConnect();

    public void setPatient(PatientHealthData patientData) {
        this.currentPatientData = patientData;

        firstNameField.setText(patientData.getFirstName());
        lastNameField.setText(patientData.getLastName());
        ageField.setText(String.valueOf(patientData.getAge()));
        phoneField.setText(patientData.getPhone());
        genderField.setText(patientData.getGender());
        emailField.setText(patientData.getEmail());
        bloodGroupField.setText(patientData.getBloodGroup());
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> {
            try {
                saveChanges();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        cancelButton.setOnAction(event -> closeDialog());
    }

    private void saveChanges() throws SQLException {
        currentPatientData.setFirstName(firstNameField.getText());
        currentPatientData.setLastName(lastNameField.getText());
        currentPatientData.setAge(Integer.parseInt(ageField.getText()));
        currentPatientData.setPhone(phoneField.getText());
        currentPatientData.setGender(genderField.getText());
        currentPatientData.setEmail(emailField.getText());
        currentPatientData.setBloodGroup(bloodGroupField.getText());

        updatePatientInDatabase(currentPatientData);
        closeDialog();
    }

    private void updatePatientInDatabase(PatientHealthData patientData) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, age = ?, phone_number = ?, gender = ?, email = ?, blood_group = ? WHERE email = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query); ){

            statement.setString(1, patientData.getFirstName());
            statement.setString(2, patientData.getLastName());
            statement.setInt(3, patientData.getAge());
            statement.setString(4, patientData.getPhone());
            statement.setString(5, patientData.getGender());
            statement.setString(6, patientData.getEmail());
            statement.setString(7, patientData.getBloodGroup());
            statement.setString(8, patientData.getEmail());

            statement.executeUpdate();
            showAlert("Data Modified", "Data updated successfully");

        }catch (Exception e) {
            e.printStackTrace();
            showAlert("Error in Data Modification", "Data not updated, Try again Later !");
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
