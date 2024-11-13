package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.UserDataManager;
import com.zodiac.homehealthdevicedatalogger.MainApplication;
import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;

import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PatientDashboardController {
    public Button btnAddHealthData;
    @FXML
    private Button btnPatientLogout;
    @FXML
    private TableView<?> healthRecordsTable;

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
}
