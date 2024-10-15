package com.zodiac.homehealthdevicedatalogger;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController extends Application {


    public Button loginButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    public void handleSignUp(MouseEvent event) throws IOException {
        URL fxmlLocation = getClass().getResource("SignUp.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void handleSubmit(MouseEvent mouseEvent) throws IOException {

            URL fxmlLocation = getClass().getResource("PatientDashboard.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
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
}
