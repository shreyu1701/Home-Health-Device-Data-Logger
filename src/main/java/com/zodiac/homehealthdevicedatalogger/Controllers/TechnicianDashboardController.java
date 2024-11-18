package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.zodiac.homehealthdevicedatalogger.Controllers.PatientDashboardController.logout;

public class TechnicianDashboardController  {

    public Button btnTechnicianLogout;

    @FXML
    private Label lblFullName;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblBloodGroup;


    private static final String FILE_PATH = "UserData.json";
    private ObjectMapper mapper = new ObjectMapper();
    User technicianData;
    public void initialize() {
        loadTechnicianData(technicianData);
    }

    public void loadTechnicianData(User technicianData) {
        if (null != technicianData) {
            lblFullName.setText(technicianData.getFirstName() + " " + technicianData.getLastName());
            lblAge.setText(String.valueOf(technicianData.getAge()));
            //  emailLabel.setText("Email: "+patientData.getEmail());
            lblGender.setText(technicianData.getGender());
            lblBloodGroup.setText(technicianData.getBloodGroup());
        } else {
            // Handle null user scenario if needed, for instance, show an error or default text
            System.out.println("User data is null.");
        }

    }


    public void handleTechnicianLogout(ActionEvent actionEvent) throws IOException {
        logout(btnTechnicianLogout);
    }

    public void handleAddPatient(ActionEvent actionEvent) {
        System.out.println("Add Patient");
    }
}
