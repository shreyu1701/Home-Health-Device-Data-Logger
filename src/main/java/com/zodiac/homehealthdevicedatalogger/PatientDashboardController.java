package com.zodiac.homehealthdevicedatalogger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PatientDashboardController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private Button addHealthData;

    @FXML
    public void handleAddHealthData(ActionEvent mouseEvent) throws IOException {

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Button was clicked!");
//        alert.showAndWait();

        URL fxmlLocation = getClass().getResource("AddHealthData.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void start(Stage primaryStage) {
    launch();
    }
}
