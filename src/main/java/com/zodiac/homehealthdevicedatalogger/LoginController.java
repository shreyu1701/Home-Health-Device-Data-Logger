package com.zodiac.homehealthdevicedatalogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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


    // Login button Starts
    @FXML
    public void handleLogin(ActionEvent mouseEvent) throws IOException {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Validate the email and password
        if (isValidEmail(email) && isValidPassword(password)) {
            errorLabel.setText("Login successful!");
            URL fxmlLocation = getClass().getResource("PatientDashboard.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage closestage = (Stage) btnLoginSubmit.getScene().getWindow();
            closestage.close();
        } else {
            errorLabel.setText("Invalid email or password.");
        }
    }

    private boolean isValidPassword (String password){
        return password != null && password.length() >= 8;
    }

//        else if (role.equals("technician")) {
//            URL fxmlLocation = getClass().getResource("TechnicianDashboard.fxml");
//            FXMLLoader loader = new FXMLLoader(fxmlLocation);
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        }
//    }

    //Login  Button Ends

    //Forget Password Starts
    @FXML
    public void handleForgetPassword(ActionEvent event) throws IOException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Forgot Password");
        dialog.setHeaderText("Enter your email address");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(email -> {
            if (isValidEmail(email)) {
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
    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailPattern);
    }
    //Send Reset Link
    private void sendPasswordResetLink(Object email) {
        System.out.println("Password reset link sent to: " + email);
    }
    //Forget Password Ends


    //Sign Up Button
    @FXML
    public void handleSignUp(MouseEvent event) throws IOException {
        URL fxmlLocation = getClass().getResource("SignUp.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage closestage = (Stage) btnSignUp.getScene().getWindow();
        closestage.close();

    }
}
