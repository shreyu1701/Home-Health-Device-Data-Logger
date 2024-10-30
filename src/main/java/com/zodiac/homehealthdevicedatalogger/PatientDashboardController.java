package com.zodiac.homehealthdevicedatalogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PatientDashboardController {
    @FXML
    private Button btnPatientLogout;


    @FXML
    public void handlePatientLogout(ActionEvent actionEvent) throws IOException {
        logout(btnPatientLogout);
    }

    static void logout(Button btnPatientLogout) throws IOException {
        Stage closestage = (Stage) btnPatientLogout.getScene().getWindow();
        closestage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        URL root = fxmlLoader.getClass().getResource("Login.fxml");
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void handleAddHealthData(ActionEvent mouseEvent) throws IOException {

        URL fxmlLocation = getClass().getResource("AddHealthData.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }
}
