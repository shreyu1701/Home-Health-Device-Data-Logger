package com.zodiac.homehealthdevicedatalogger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HelloApplication is the main entry point for the JavaFX application.
 * It extends the Application class and overrides the start method to set up the primary stage.
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/Login.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Health Device Data Logger");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}