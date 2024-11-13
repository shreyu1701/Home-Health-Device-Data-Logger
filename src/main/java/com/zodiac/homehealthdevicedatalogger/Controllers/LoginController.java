package com.zodiac.homehealthdevicedatalogger.Controllers;


import com.zodiac.homehealthdevicedatalogger.Util.EmailService;
import com.zodiac.homehealthdevicedatalogger.Validation.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import com.zodiac.homehealthdevicedatalogger.Data.UserDataManager;
import com.zodiac.homehealthdevicedatalogger.Models.User;

import javax.mail.MessagingException;

public class LoginController {


    @FXML
    private Label errorLabel;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnLoginSubmit;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Hyperlink linkForgetPassword;

    private final UserDataManager userDataManager = new UserDataManager();
    InputValidator inputValidator = new InputValidator();
    EmailService emailService = new EmailService();

    // Login button Starts
    @FXML
    public void handleLogin(ActionEvent mouseEvent) throws IOException {


            String email = txtEmail.getText();
            String password = txtPassword.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter both email and password.");
                return;
            }

            // Validate login
            User user = inputValidator.validateUser(email, password);
            if (user != null) {
                if ("Technician".equals(user.getRole())) {
                    // Redirect to Technician Dashboard
                    URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/TechnicianDashboard.fxml");
                    GUILoader(fxmlLocation, btnLoginSubmit);

                } else if ("Patient".equals(user.getRole())) {
                    // Redirect to Patient Dashboard
                    URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/PatientDashboard.fxml");
                    GUILoader(fxmlLocation, btnLoginSubmit);
                }
            } else {
                showAlert("Login Failed", "Invalid email or password.");
            }
        }



        // Show alert messages
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

    //Forget Password Starts
    @FXML
    public void handleForgetPassword(ActionEvent event) throws IOException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Forgot Password");
        dialog.setHeaderText("Enter your email address");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(email -> {
            if (inputValidator.isValidEmail(email)) {

                sendPasswordResetLink(email);

                showConfirmationDialog("Password reset link has been sent to your email.");
            } else {
                showErrorDialog("Invalid email address. Please try again.");
            }
        });

    }




    //Show Error Dialog Message Box
    private void showErrorDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
    //Show Confirmation Dialog Message Box
    private void showConfirmationDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email Sent");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
    //Valid Email (Login Email && Forget Password Email)

    //Send Reset Link
    private void sendPasswordResetLink(String email) {
        try {
            emailService.sendPasswordResetLink(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Password reset link sent to: " + email);
    }
    //Forget Password Ends


    //Sign Up Button
    @FXML
    public void handleSignUp(ActionEvent event) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/SignUp.fxml");
        GUILoader(fxmlLocation, btnSignUp);

    }

    protected static void GUILoader(URL fxmlLocation, Button btnAction) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage closestage = (Stage) btnAction.getScene().getWindow();
        closestage.close();
    }
}