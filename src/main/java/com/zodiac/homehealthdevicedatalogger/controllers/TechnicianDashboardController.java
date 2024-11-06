package com.zodiac.homehealthdevicedatalogger.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.zodiac.homehealthdevicedatalogger.Controllers.PatientDashboardController.logout;

public class TechnicianDashboardController  {

    public Button btnTechnicianLogout;


    public void handleTechnicianLogout(ActionEvent actionEvent) throws IOException {
        logout(btnTechnicianLogout);
    }

}
