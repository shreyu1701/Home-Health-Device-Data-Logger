package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;

import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PatientDashboardController {
    public Button btnAddHealthData;
    @FXML
    private Button btnPatientLogout;
    @FXML
    private TableView<?> healthRecordsTable;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label emailLabel;



    private User currentUser;

//    public void initialize() {
//        currentUser = new UserDataManager.getLoggedInUser();
//
//        userName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
//        Role.setText(currentUser.getRole());
//    }

    @FXML
    public void handlePatientLogout(ActionEvent actionEvent) throws IOException {
        logout(btnPatientLogout);
    }


    @FXML
    public void handleAddHealthData(ActionEvent mouseEvent) throws IOException {

        URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/AddHealthData.fxml");
        GUILoader(fxmlLocation,btnAddHealthData);
    }


    // Method to load user data from JSON file
    public void loadUserData(User patientData) throws IOException {
        if (patientData != null) {
            nameLabel.setText(patientData.getFirstName() + " " + patientData.getLastName());
            ageLabel.setText(String.valueOf(patientData.getAge()));
            genderLabel.setText(patientData.getGender());
            bloodGroupLabel.setText(patientData.getBloodGroup());
        } else {
            // Handle null user scenario if needed, for instance, show an error or default text
            System.out.println("User data is null.");
        }
    }
    private User findUserById(List<User> users, int userId) {
        //int userId = Integer.parseInt(userId);

        for (User user : users) {
            if ((String.valueOf(user.getId()).equals(String.valueOf(userId)))) {
                return user;
            }
        }
        return null; // User not found
    }

    static void logout(Button btnPatientLogout) throws IOException {
        Stage closestage = (Stage) btnPatientLogout.getScene().getWindow();
        closestage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(PatientDashboardController.class.getResource("/com/zodiac/homehealthdevicedatalogger/Views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


    // Inner classes to represent the structure of User data from JSON
    static class UserData {
        private List<User> users;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }
}
