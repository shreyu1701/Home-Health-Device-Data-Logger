package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Data.UserDataManager;
import com.zodiac.homehealthdevicedatalogger.Util.IDGenerator;
import com.zodiac.homehealthdevicedatalogger.Util.Util;
import com.zodiac.homehealthdevicedatalogger.Validation.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;

public class SignUpController {

    @FXML
    public Button btnBackLogin;
//    @FXML
//    private TextField txtUniqueID;
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
    private ChoiceBox choiceBloodGroup;
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


    InputValidator inputValidator = new InputValidator();
    IDGenerator idGenerator = new IDGenerator();

    Util util = new Util();

    // Sign-Up Button Starts
    @FXML
    public void handleSignUp(ActionEvent actionEvent) throws IOException, SQLException {
      //  String id = getId();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        int age = Integer.parseInt(txtAge.getText().trim());
        String phone = txtPhoneNumber.getText().trim();
        String gender = (String) choiceGender.getValue();
        String role = (String) choiceRole.getValue();
        String bloodGroup = (String) choiceBloodGroup.getValue();
        String email = txtEmail.getText().trim();
        String password = paswdPassword.getText().trim();
        String confirmPassword = paswdConfirmPassword.getText().trim();
        String roleId = idGenerator.generateNewId(role);

        // Validate inputs
        if (!inputValidator.isValidName(firstName)) {
            errorLabel.setText("Invalid first name.");
        } else if (!inputValidator.isValidName(lastName)) {
            errorLabel.setText("Invalid last name.");
        } else if (!inputValidator.isValidAge(age)) {
            errorLabel.setText("Invalid age.");
        } else if (!inputValidator.isValidPhoneNumber(phone)) {
            errorLabel.setText("Invalid phone number.");
        } else if (gender == null) {
            errorLabel.setText("Please select a gender.");
        } else if (role == null) {
            errorLabel.setText("Please select a role.");
        } else if (!inputValidator.isValidEmail(email)) {
        } else if (bloodGroup == null) {
            errorLabel.setText("Please select a blood group.");
        } else if (!inputValidator.isValidEmail(email)) {
            errorLabel.setText("Invalid email format.");
        } else if (!String.valueOf(inputValidator.isValidPassword(password)).isEmpty()) {
            errorLabel.setText(inputValidator.isValidPassword(password));
        } else if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
        } else {
            String uniqueId = util.getId();
            User user = new User(uniqueId , firstName, lastName, age, phone, gender, role, bloodGroup,  email, password, confirmPassword,roleId);

            UserDataManager userDataManager = new UserDataManager();
            userDataManager.saveUser(user);
            // Notify user of success

            // Everything is valid, process the sign-up (e.g., save to the database)
            errorLabel.setText("Sign up successful!");
            clearForm();
            PatientDashboardController.logout(btnSignUp);

        }
    }


    // Helper method to clear form after successful sign-up
    private void clearForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAge.clear();
        txtPhoneNumber.clear();
        choiceGender.setValue(null);
        choiceRole.setValue(null);
        choiceBloodGroup.setValue(null);
        txtEmail.clear();
        paswdPassword.clear();
        paswdConfirmPassword.clear();
    }

    public void handleBackLogin(ActionEvent actionEvent) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/Login.fxml");
        GUILoader(fxmlLocation, btnBackLogin);
    }

    //Sign-Up Button Ends
}
