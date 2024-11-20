package com.zodiac.homehealthdevicedatalogger.Controllers;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
//import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Models.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import oracle.sql.DATE;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientDashboardController {
    public Button btnAddHealthData;
    @FXML
    private Button btnPatientLogout;
//    @FXML
//    private TableView<?> healthRecordsTable;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TableView<Patient> healthRecordsTable;

    @FXML
    private
    TableColumn<Patient, LocalDate> dateColumn;
    @FXML
    private TableColumn<Patient, String> bloodPressureColumn;
    @FXML
    private TableColumn<Patient, String> sugarLevelColumn;
    @FXML
    private TableColumn<Patient, String> heartRateColumn;
    @FXML
    private TableColumn<Patient, String> oxygenLevelColumn;
    @FXML
    private TableColumn<PatientHealthData, Void> updateColumn;
    @FXML
    private TableColumn<Patient, Void> columnDelete;


    // ObservableList for TableView data
    private ObservableList<Patient> healthDataList = FXCollections.observableArrayList();

    // Initialize method
    public void initialize(User user) {
        // Set up columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        sugarLevelColumn.setCellValueFactory(new PropertyValueFactory<>("sugarLevel"));
        heartRateColumn.setCellValueFactory(new PropertyValueFactory<>("heartRate"));
        oxygenLevelColumn.setCellValueFactory(new PropertyValueFactory<>("oxygenLevel"));


        // Format the date column
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        dateColumn.setCellFactory(column -> new TableCell<PatientHealthData, Date>() {
//            @Override
//            protected void updateItem(Date date, boolean empty) {
//                super.updateItem(date, empty);
//                if (empty || date == null) {
//                    setText(null);
//                } else {
//                    setText(date.toGMTString(formatter));
//                }
//            }
//        });
        // Load data from the database
        loadHealthData(user);
        // Add delete button column
        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable(){
        columnDelete.setCellFactory(param ->new TableCell<>()

        {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    Patient data = getTableView().getItems().get(getIndex());
                    deleteHealthData(data);
                });
            }

            @Override
            protected void updateItem (Void item,boolean empty){
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void deleteHealthData(Patient healthData) {
        String deleteQuery = "DELETE FROM HealthData WHERE data_date = ? AND blood_pressure = ? AND sugar_level = ? AND heart_rate = ? AND oxygen_level = ?";
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Set all parameters for the WHERE clause
//            preparedStatement.setString(1, healthData.getUserId());
            preparedStatement.setDate(1, java.sql.Date.valueOf(healthData.getDate())); // Assuming date is not null
            preparedStatement.setString(2, healthData.getBloodPressure());
            preparedStatement.setString(3, healthData.getSugarLevel());
            preparedStatement.setInt(4, healthData.getHeartRate());
            preparedStatement.setInt(5, healthData.getOxygenLevel());
           // preparedStatement.setString(7, healthData.getComment());

            // Execute the deletion
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert("Success", "Health record deleted successfully.");
                healthRecordsTable.getItems().remove(healthData); // Remove from TableView
            } else {
                showAlert("Error", "Failed to delete the health record. Record may not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while deleting the health record.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void loadHealthData(User user) {


        String dbUrl = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
        String dbUser = "n01660845";
        String dbPassword = "oracle";
        String userID = user.getId();
        String query = "SELECT USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS FROM HealthData WHERE USER_ID = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query);){
            // Replace with the actual user ID you want to filter by
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();



        while (resultSet.next()) {

            // Handle null dates
            LocalDate dataDate = null;
            if (resultSet.getDate("DATA_DATE") != null) {
                dataDate = resultSet.getDate("DATA_DATE").toLocalDate();
            }

            Patient healthData = new Patient(
                    dataDate,
                    resultSet.getString("blood_pressure"),
                    resultSet.getString("sugar_level"),
                    resultSet.getInt("heart_rate"),
                    resultSet.getInt("oxygen_level"),
                    resultSet.getString("comments")
            );

                // Add each record to the ObservableList

                healthDataList.add(healthData);
            }

            // Set the data in the TableView
            healthRecordsTable.setItems(healthDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    UserSession userSession ;

//    public void initialize() {
//        currentUser = new UserDataManager.getLoggedInUser(User user);
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
//        User currentUser = UserSession.getInstance().getCurrentUser();

        URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/AddHealthData.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    // Method to load user data from JSON file
    public void loadUserData(User patientData) throws IOException {
        if (patientData != null) {
            nameLabel.setText(patientData.getFirstName() + " " + patientData.getLastName());
            ageLabel.setText(String.valueOf(patientData.getAge()));
            genderLabel.setText(patientData.getGender());
            bloodGroupLabel.setText(patientData.getBloodGroup());
        } else {
            // Handle null user scenario if needed, for instance, show an error or default text
            System.out.println("User data is null.");
        }
    }
    private User findUserById(List<User> users, int userId) {
        //int userId = Integer.parseInt(userId);

        for (User user : users) {
            if ((String.valueOf(user.getId()).equals(String.valueOf(userId)))) {
                return user;
            }
        }
        return null; // User not found
    }




    public void clearDashboard() {
        healthRecordsTable.getItems().clear(); // Clear the TableView
        healthRecordsTable.setPlaceholder(new Label("No health data available.")); // Optional placeholder
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


    // Inner classes to represent the structure of User data from JSON
    static class UserData {
        private List<User> users;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }
}
