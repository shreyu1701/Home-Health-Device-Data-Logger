package com.zodiac.homehealthdevicedatalogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * HelloApplication is the main entry point for the JavaFX application.
 * It extends the Application class and overrides the start method to set up the primary stage.
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        URL root = fxmlLoader.getClass().getResource("Login.fxml");
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}