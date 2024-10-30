package com.zodiac.homehealthdevicedatalogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SignUpController extends MainApplication {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ChoiceBox choiceGender;
    @FXML
    private ChoiceBox choiceRole;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField paswdPassword;
    @FXML
    private PasswordField paswdConfirmPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Label errorLabel;

    // Sign-Up Button Starts
    @FXML
    public void handleSignUp(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String age = txtAge.getText();
        String phone = txtPhoneNumber.getText();
        String gender = (String) choiceGender.getValue();
        String role = (String) choiceRole.getValue();
        String email = txtEmail.getText();
        String password = paswdPassword.getText();
        String confirmPassword = paswdConfirmPassword.getText();

        // Validate inputs
        if (!isValidName(firstName)) {
            errorLabel.setText("Invalid first name.");
        } else if (!isValidName(lastName)) {
            errorLabel.setText("Invalid last name.");
        } else if (!isValidAge(age)) {
            errorLabel.setText("Invalid age.");
        } else if (!isValidPhoneNumber(phone)) {
            errorLabel.setText("Invalid phone number.");
        } else if (gender == null) {
            errorLabel.setText("Please select a gender.");
        } else if (role == null) {
            errorLabel.setText("Please select a role.");
        } else if (!isValidEmail(email)) {
            errorLabel.setText("Invalid email format.");
        } else if (!isValidPassword(password)) {
            errorLabel.setText("Password must be at least 8 characters long.");
        } else if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
        } else {
            // Everything is valid, process the sign-up (e.g., save to the database)
            errorLabel.setText("Sign up successful!");
            clearForm();
            PatientDashboardController.logout(btnSignUp);

        }
    }

    // Helper method to validate name
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
    }

    // Helper method to validate age (must be a number)
    private boolean isValidAge(String age) {
        try {
            int ageNumber = Integer.parseInt(age);
            return ageNumber > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to validate phone number (basic pattern)
    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Helper method to validate email
    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailPattern);
    }

    // Helper method to validate password
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    // Helper method to clear form after successful sign-up
    private void clearForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAge.clear();
        txtPhoneNumber.clear();
        choiceGender.setValue(null);
        choiceRole.setValue(null);
        txtEmail.clear();
        paswdPassword.clear();
        paswdConfirmPassword.clear();
    }

    //Sign-Up Button Ends
}
