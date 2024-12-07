package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
//import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.HealthRecord;
import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;
import static com.zodiac.homehealthdevicedatalogger.Controllers.PatientDashboardController.logout;

public class TechnicianDashboardController {
    //For History section
    public Button btnTechnicianLogout;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblBloodGroup;
    @FXML
    private Button btnAddPatient;
    @FXML
    private TableView<PatientHealthData> healthRecordsTable;
    @FXML
    private TableColumn<PatientHealthData, String> columnDate;
    @FXML
    private TableColumn<PatientHealthData, String> columnUser;
    @FXML
    private TableColumn<PatientHealthData, String> columnBloodPressure;
    @FXML
    private TableColumn<PatientHealthData, String> columnSugar;
    @FXML
    private TableColumn<PatientHealthData, Integer> columnHeartRate;
    @FXML
    private TableColumn<PatientHealthData, Integer> columnOxygen;
    @FXML
    public TableColumn<PatientHealthData, HBox> actionButtons;
    @FXML
    private TextField searchField;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;


    //For Dashboard Section
    @FXML
    private TableView<PatientHealthData> healthRecordsTable1;
    @FXML
    private TableColumn<PatientHealthData, String> columnDate1;
    @FXML
    private TableColumn<PatientHealthData, String> columnUser1;
    @FXML
    private TableColumn<PatientHealthData, String> columnBloodPressure1;
    @FXML
    private TableColumn<PatientHealthData, String> columnSugar1;
    @FXML
    private TableColumn<PatientHealthData, Integer> columnHeartRate1;
    @FXML
    private TableColumn<PatientHealthData, Integer> columnOxygen1;
    @FXML
    public TableColumn<PatientHealthData, HBox> actionButtons1;

    // For Setting Section

    @FXML
    private TextField userIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField emailField;
//    @FXML
//    private TextField dobField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField roleField;
    @FXML
    private TextField bloodGroupField;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    //For Patient Managemnet

    @FXML
    private TableView<PatientHealthData> patientTableView;
    @FXML
    private TableColumn<PatientHealthData, String> firstNameColumn;
    @FXML
    private TableColumn<PatientHealthData, String> lastNameColumn;
    @FXML
    private TableColumn<PatientHealthData, Integer> ageColumn;
    @FXML
    private TableColumn<PatientHealthData, String> phoneColumn;
    @FXML
    private TableColumn<PatientHealthData, String> genderColumn;
    @FXML
    private TableColumn<PatientHealthData, String> emailColumn;
    @FXML
    private TableColumn<PatientHealthData, String> bloodGroupColumn;
    @FXML
    private TableColumn<PatientHealthData, Void> actionsColumn;

    private ObservableList<PatientHealthData> patientList = FXCollections.observableArrayList();

    DBConnect dbConnect= new DBConnect();



    public void initialize(User user) throws SQLException {
        // Set up columns to bind to PatientHealthData properties
        columnDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        columnUser.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserId()));
        columnBloodPressure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBloodPressure()));
        columnSugar.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSugarLevel()));
        columnHeartRate.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getHeartRate()).asObject());
        columnOxygen.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOxygenLevel()).asObject());

        columnDate1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        columnUser1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserId()));
        columnBloodPressure1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBloodPressure()));
        columnSugar1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSugarLevel()));
        columnHeartRate1.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getHeartRate()).asObject());
        columnOxygen1.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOxygenLevel()).asObject());

        // Set up table columns for Patient Managemnet

        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        bloodGroupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBloodGroup()));



        ObservableList<PatientHealthData> healthDataList = FXCollections.observableArrayList();

        actionButtons.setCellValueFactory(data -> {
            HBox actionButtons = new HBox();
            actionButtons.setSpacing(10);

            Button updateButton = new Button("Update");
            Button deleteButton = new Button("Delete");

            // Update Button Action
            updateButton.setOnAction(event -> openUpdateDialog(data.getValue(), user));
            updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");


            // Delete Button Action
            deleteButton.setOnAction(event -> deleteHealthData(data.getValue()));
            deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");


            actionButtons.getChildren().addAll(updateButton, deleteButton);
            return new SimpleObjectProperty<>(actionButtons);

        });


        actionButtons1.setCellValueFactory(data -> {
            HBox actionButtons = new HBox();
            actionButtons.setSpacing(10);

            Button updateButton = new Button("Update");
            Button deleteButton = new Button("Delete");

            // Update Button Action
            updateButton.setOnAction(event -> openUpdateDialog(data.getValue(), user));
            updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            // Delete Button Action
            //deleteButton.setOnAction(event -> deleteRecord(data.getValue()));
            deleteButton.setOnAction(event -> deleteHealthData(data.getValue()));
            deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            actionButtons.getChildren().addAll(updateButton, deleteButton);
            return new SimpleObjectProperty<>(actionButtons);

        });

        // Add action buttons to Patient Management
        // Add action buttons
        actionsColumn.setCellFactory(column -> new TableCell<PatientHealthData, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(10);

                    Button updateButton = new Button("Update");
                    Button deleteButton = new Button("Delete");

                    // Fetch the current PatientHealthData object for the row
                    PatientHealthData currentPatientData = getTableView().getItems().get(getIndex());

                    // Set Update button action
                    updateButton.setOnAction(event -> openUpdatePatientDialog(currentPatientData));
                    updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

                    // Set Delete button action
                    deleteButton.setOnAction(event -> deletePatientHealthData(currentPatientData));
                    deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

                    // Add buttons to the HBox
                    actionButtons.getChildren().addAll(updateButton, deleteButton);

                    // Set the HBox as the graphic for the cell
                    setGraphic(actionButtons);
                }
            }
        });


        // Load data from the database
        loadPatients();
        // Add delete button column
        //addDeleteButtonToTable();
        healthRecordsTable.setItems(healthDataList);
        healthRecordsTable1.setItems(healthDataList);


        // Add listeners for search and date range filters
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());


    }

//"/com/zodiac/homehealthdevicedatalogger/Views/UpdatePatientData.fxml"
    private void openUpdatePatientDialog(PatientHealthData patientData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/UpdatePatientData.fxml"));
            Parent root = loader.load();

            UpdatePatientController controller = loader.getController();
            controller.setPatient(patientData);

            Stage stage = new Stage();
            stage.setTitle("Update Patient");
            stage.setScene(new Scene(root));

            // Show the dialog and wait for it to close
            stage.setOnHiding(event -> {
                try {
                    loadPatients();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }); // Refresh the table after dialog closes
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deletePatientHealthData(PatientHealthData patientData) {
        String query = "DELETE FROM USERS WHERE EMAIL = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setString(1, patientData.getEmail().toString());


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Remove the deleted item from the table view
                patientTableView.getItems().remove(patientData);
                System.out.println("Deleted patient health data for EMail: " + patientData.getEmail());
                showAlert("Data removed", "Data Deleted Successfully");
            } else {
                System.out.println("Failed to delete patient health data for User ID: " + patientData.getUserId());
                showAlert("Error in Data removed", "Failed to delete patient data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadPatients() throws SQLException {
        patientList.clear();
        String query = "SELECT first_name, last_name, age, phone_number, gender, email, blood_group FROM users WHERE role_name = 'Patient'";
        try (Connection connection = dbConnect.getConnection()
             ;Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone_number");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String bloodGroup = resultSet.getString("blood_group");

                patientList.add(new PatientHealthData(firstName, lastName, age, phone, gender, email, bloodGroup));
            }

            patientTableView.setItems(patientList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openUpdateDialog(PatientHealthData healthData, User user) {
        // Open an update dialog
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/UpdateHealthDataDialog.fxml"));
            Parent root = loader.load();
            UpdateHealthDataController updateController = loader.getController();

            updateController.setInitialData(healthData, user);
            //UpdateHealthDataController updateController = new UpdateHealthDataController();
            updateController.showUpdateDialog(healthData);



            // Create a new stage
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Health Data");
            updateStage.setScene(new Scene(root));
            updateStage.showAndWait();

            healthDataList.clear();
            loadAllHealthData();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void applyFilters() {
        String userId = searchField.getText();
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        loadHealthRecords(userId, fromDate, toDate);
    }


    private void loadHealthRecords(String userId, LocalDate fromDate, LocalDate toDate) {
        ObservableList<PatientHealthData> recordsList = FXCollections.observableArrayList();
        String query = "SELECT TO_CHAR(DATA_DATE, 'YYYY-MM-DD') AS formatted_date, USER_ID, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL " +
                "FROM HealthData WHERE 1=1";

        // Add optional filters to the query
        if (userId != null && !userId.isEmpty()) {
            query += " AND USER_ID LIKE ?";
        }
        if (fromDate != null) {
            query += " AND DATA_DATE >= TO_DATE(?, 'YYYY-MM-DD')";
        }
        if (toDate != null) {
            query += " AND DATA_DATE <= TO_DATE(?, 'YYYY-MM-DD')";
        }

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int paramIndex = 1;

            // Set parameters dynamically
            if (userId != null && !userId.isEmpty()) {
                statement.setString(paramIndex++, "%" + userId + "%"); // Wildcards for LIKE search
            }
            if (fromDate != null) {
                statement.setString(paramIndex++, fromDate.toString()); // Convert LocalDate to String
            }
            if (toDate != null) {
                statement.setString(paramIndex++, toDate.toString()); // Convert LocalDate to String
            }

            ResultSet resultSet = statement.executeQuery();
            recordsList.clear();

            while (resultSet.next()) {
                recordsList.add(new PatientHealthData(
                        LocalDate.parse(resultSet.getString("formatted_date")),
                        resultSet.getString("user_id"),
                        resultSet.getString("blood_pressure"),
                        resultSet.getString("sugar_level"),
                        resultSet.getString("heart_rate"),
                        resultSet.getString("oxygen_level")
                ));
            }

            healthRecordsTable.setItems(recordsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deletePatient(PatientHealthData patient) {
        String query = "DELETE FROM users WHERE email = ?";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, patient.getEmail());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                patientList.remove(patient);
                System.out.println("Deleted patient: " + patient.getFirstName() + " " + patient.getLastName());
            } else {
                System.out.println("Failed to delete patient.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteHealthData(PatientHealthData healthData) {
        String deleteQuery = "DELETE FROM HealthData WHERE user_id = ? AND data_date = ? AND blood_pressure = ? AND sugar_level = ? AND heart_rate = ? AND oxygen_level = ? AND comments = ?";
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Set all parameters for the WHERE clause
            preparedStatement.setString(1, healthData.getUserId());
            preparedStatement.setDate(2, java.sql.Date.valueOf(healthData.getDate())); // Assuming date is not null
            preparedStatement.setString(3, healthData.getBloodPressure());
            preparedStatement.setString(4, healthData.getSugarLevel());
            preparedStatement.setInt(5, healthData.getHeartRate());
            preparedStatement.setInt(6, healthData.getOxygenLevel());
            preparedStatement.setString(7, healthData.getComment());

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

    // ObservableList for TableView data
    private ObservableList<PatientHealthData> healthDataList = FXCollections.observableArrayList();

    public void handleAddPatient(ActionEvent actionEvent) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/SignUp.fxml");
        GUILoader(fxmlLocation, btnAddPatient);
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

//    public void loadAllHealthData() {
//        String query = "SELECT user_id, data_date, blood_pressure, sugar_level, heart_rate, oxygen_level, comments ,creationDateTime FROM HealthData";
//        try (Connection connection = DBConnect.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//             ObservableList<PatientHealthData> healthDataList = FXCollections.observableArrayList();
//             // Map ResultSet to HealthData objects
//            while (resultSet.next()) {
//                // Handle null dates
//                LocalDate dataDate = null;
//                if (resultSet.getDate("data_date") != null) {
//                    dataDate = resultSet.getDate("data_date").toLocalDate();
//                }
//                PatientHealthData healthData = new PatientHealthData(
//                        resultSet.getString("user_id"),
//                        dataDate,
//                        resultSet.getString("blood_pressure"),
//                        resultSet.getString("sugar_level"),
//                        resultSet.getInt("heart_rate"),
//                        resultSet.getInt("oxygen_level"),
//                        resultSet.getString("comments"),
//                        resultSet.getTimestamp("creationDateTime").toLocalDateTime()
//                );
//                healthDataList.add(healthData);
//            }
//
//            // Bind the data to the TableView
//            healthRecordsTable.setItems(healthDataList);
//            healthRecordsTable1.setItems(healthDataList);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//    }


    public void loadAllHealthData() {
        // Updated query with ORDER BY clause
        String query = "SELECT user_id, data_date, blood_pressure, sugar_level, heart_rate, oxygen_level, comments, creationDateTime " +
                "FROM HealthData ORDER BY creationDateTime DESC";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<PatientHealthData> healthDataList = FXCollections.observableArrayList();

            // Map ResultSet to HealthData objects
            while (resultSet.next()) {
                // Handle null dates
                LocalDate dataDate = null;
                if (resultSet.getDate("data_date") != null) {
                    dataDate = resultSet.getDate("data_date").toLocalDate();
                }

                PatientHealthData healthData = new PatientHealthData(
                        resultSet.getString("user_id"),
                        dataDate,
                        resultSet.getString("blood_pressure"),
                        resultSet.getString("sugar_level"),
                        resultSet.getInt("heart_rate"),
                        resultSet.getInt("oxygen_level"),
                        resultSet.getString("comments"),
                        resultSet.getTimestamp("creationDateTime").toLocalDateTime()
                );

                healthDataList.add(healthData);
            }

            // Bind the data to the TableView components
            healthRecordsTable.setItems(healthDataList);

            ObservableList<PatientHealthData> topTenRecords = FXCollections.observableArrayList();
            if (!healthDataList.isEmpty()) {
                int limit = Math.min(10, healthDataList.size());
                topTenRecords.addAll(healthDataList.subList(0, limit));
            }
            healthRecordsTable1.setItems(topTenRecords);

            //healthRecordsTable1.setItems(healthDataList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions (logging, user notifications, etc.)
        }
    }



    public void handleTechnicianLogout(ActionEvent actionEvent) throws IOException {
        logout(btnTechnicianLogout);
    }

    public void loadDataTechnicianDetail(User user) {

        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER, EMAIL, ROLE_NAME, ROLE_ID, BLOOD_GROUP FROM USERS WHERE USER_ID = ? AND ROLE_NAME = 'Technician'";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getId()); // Replace with dynamic ID as needed
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userIdField.setText(rs.getString("ROLE_ID"));
                firstNameField.setText(rs.getString("FIRST_NAME"));
                lastNameField.setText(rs.getString("LAST_NAME"));
                ageField.setText(rs.getString("AGE"));
                phoneField.setText(rs.getString("PHONE_NUMBER"));
                genderField.setText(rs.getString("GENDER"));
                emailField.setText(rs.getString("EMAIL"));
                roleField.setText(rs.getString("ROLE_NAME"));
                bloodGroupField.setText(rs.getString("BLOOD_GROUP"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAddPatientHealthData(ActionEvent actionEvent) throws IOException {
        //GUI of Technician AddHealthData
        FXMLLoader fxmlLoader = new FXMLLoader(PatientDashboardController.class.getResource("/com/zodiac/homehealthdevicedatalogger/Views/TechnicianAddHealthData.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Health Data");
        stage.setScene(scene);
        stage.show();
    }

    public void handleRefresh(ActionEvent actionEvent) {

        {
//            healthDataList.clear();
//            healthRecordsTable1.setItems(healthDataList);
//            healthRecordsTable.setItems(healthDataList);
            // Call loadHealthData to refresh the table
            loadAllHealthData();

        }
    }

    public void onEditButtonClick(ActionEvent actionEvent) {

        // Allow the user to edit the fields
        toggleEditableFields(true);
        showAlert("Modify Content","You Can Edit Fields");
    }
    private void toggleEditableFields(boolean editable) {
        firstNameField.setEditable(editable);
        lastNameField.setEditable(editable);
        ageField.setEditable(editable);
        phoneField.setEditable(editable);
        genderField.setEditable(editable);
        emailField.setEditable(editable);
        roleField.setEditable(editable);
        bloodGroupField.setEditable(editable);
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
    // Retrieve updated details from the fields
        String userId = userIdField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        String phone = phoneField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        String role = roleField.getText();
        String bloodGroup = bloodGroupField.getText();

        if (userId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "User ID, First Name, Last Name, Email, and Phone Number cannot be empty.");
            return;
        }

    // Update the database
        // Update the database
        String updateQuery = "UPDATE users SET first_name = ?, last_name = ?, age = ?, phone_number = ?, gender = ?, email = ?, role_name = ?, blood_group = ? WHERE role_id = ?";

        try (Connection connection = dbConnect.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, age);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, role);
            preparedStatement.setString(8, bloodGroup);
            preparedStatement.setString(9, userId);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            showAlert("Success", "Profile details updated successfully.");
            toggleEditableFields(false);
        } else {
            showAlert("Error", "Failed to update profile. Please try again.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error", "An error occurred while updating the profile: " + e.getMessage());
    }
}
}
