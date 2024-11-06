package com.zodiac.homehealthdevicedatalogger.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

public class AddHealthDataController {

    @FXML
    private ChoiceBox<String> dataChoiceBox;
    @FXML
    private HBox bloodPressureBox, sugarLevelBox, heartRateBox, oxygenLevelBox, commentBox;

    @FXML
    public void initialize() {
        // When a choice is made, update visibility of text fields
        dataChoiceBox.setOnAction(event -> updateVisibility());
    }

    private void updateVisibility() {
        String selected = dataChoiceBox.getValue();

        // Hide all text fields first
        bloodPressureBox.setVisible(false);
        sugarLevelBox.setVisible(false);
        heartRateBox.setVisible(false);
        oxygenLevelBox.setVisible(false);
        commentBox.setVisible(false);

        // Show the corresponding text field
        switch (selected) {
            case "Blood Pressure":
                bloodPressureBox.setVisible(true);
                break;
            case "Sugar Level":
                sugarLevelBox.setVisible(true);
                break;
            case "Heart Rate":
                heartRateBox.setVisible(true);
                break;
            case "Oxygen Level":
                oxygenLevelBox.setVisible(true);
                break;
            case "Comment":
                commentBox.setVisible(true);
                break;
        }
    }


    public void saveHealthData(ActionEvent actionEvent) {
    }

    public void cancelForm(ActionEvent actionEvent) {
    }
}
