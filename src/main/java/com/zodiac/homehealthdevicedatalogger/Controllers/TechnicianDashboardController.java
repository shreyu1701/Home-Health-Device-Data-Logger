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

    public void initialize() {
        loadTechnicianData();
    }

    private void loadTechnicianData() {
        try {
            // Read data from JSON file and map it to User class
            ObjectMapper objectMapper = new ObjectMapper();

            // Load the JSON data (this could be from a file, API, etc.)
            String jsonData = new String(Files.readAllBytes(Paths.get("UserData.json")));

            // Deserialize into a list of User objects
            List<User> users = objectMapper.readValue(jsonData, new TypeReference<List<User>>(){});

            // Process the list as needed (e.g., update UI, etc.)
            for (User user : users) {
                System.out.println(user.getFirstName() + " " + user.getLastName());
            }
            // Set label values
//            lblFullName.setText(user.getFirstName() + " " + user.getLastName());
//            lblAge.setText(user.getAge());
//            lblGender.setText(user.getGender());
//            lblBloodGroup.setText(user.getBloodGroup());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load technician data.");
        }
    }


    public void handleTechnicianLogout(ActionEvent actionEvent) throws IOException {
        logout(btnTechnicianLogout);
    }

    public void handleAddPatient(ActionEvent actionEvent) {
        System.out.println("Add Patient");
    }
}
