package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
//import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthDataManager;
import com.zodiac.homehealthdevicedatalogger.Models.*;
import com.zodiac.homehealthdevicedatalogger.Util.ChartsUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PatientDashboardController {
    public Button btnAddHealthData;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnSave;
    @FXML
    private Button btnRefresh;
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
    private TableColumn<Patient, LocalDate> dateColumn;
    @FXML
    private TableColumn<Patient, String> bloodPressureColumn;
    @FXML
    private TableColumn<Patient, String> sugarLevelColumn;
    @FXML
    private TableColumn<Patient, String> heartRateColumn;
    @FXML
    private TableColumn<Patient, String> oxygenLevelColumn;
    @FXML
    public TableColumn<Patient, HBox> actionButtons;

    @FXML
    private TableView<Patient> healthRecordsTable1;
    @FXML
    private TableColumn<Patient, LocalDate> dateColumn1;
    @FXML
    private TableColumn<Patient, String> bloodPressureColumn1;
    @FXML
    private TableColumn<Patient, String> sugarLevelColumn1;
    @FXML
    private TableColumn<Patient, String> heartRateColumn1;
    @FXML
    private TableColumn<Patient, String> oxygenLevelColumn1;
    @FXML
    public TableColumn<Patient, HBox> actionButtons1;

    @FXML
    private TextField UserIDField;
    @FXML
    private TextField FirstNameField;
    @FXML
    private TextField LastNameField;
    @FXML
    private TextField AgeField;
    @FXML
    private TextField PhoneNumberField;
    @FXML
    private TextField GenderField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField BloodGroupField;
    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Button btnFilterData;

//    ////BloodPressure Charts

    @FXML
    private LineChart<String, Number> bloodPressureChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    //SugarChart
    @FXML
    private AreaChart<String, Number> sugarLevelChart;

    @FXML
    private CategoryAxis xAxis_sugarLevel;

    @FXML
    private NumberAxis yAxis_sugarLevel;

    //for HeartRate
    @FXML
    private AreaChart<String, Number> heartRateChart;

    @FXML
    private CategoryAxis xAxis_heartRate;

    @FXML
    private NumberAxis yAxis_heartRate;
    //For Oxygen Level

    @FXML
    private AreaChart<String, Number> oxygenLevelChart;

    @FXML
    private CategoryAxis xAxis_oxygenLevel;

    @FXML
    private NumberAxis yAxis_oxygenLevel;

    @FXML
    private AreaChart<String, Number> healthDataChart;

    @FXML
    private CategoryAxis xAxis_fullChart;

    @FXML
    private NumberAxis yAxis_fullChart;
    @FXML
    private VBox customLegend;
//    @FXML
//    private TextField PersonalUserIDField;
//    @FXML
//    private TextField PersonalFirstNameField;
//    @FXML
//    private TextField PersonalLastNameField;
//    @FXML
//    private TextField PersonalAgeField;
//    @FXML
//    private TextField PersonalPhoneNumberField;
//    @FXML
//    private TextField PersonalGenderField;
//    @FXML
//    private TextField PersonalEmailField;
//    @FXML
//    private TextField PersonalBloodGroupField;
//    @FXML
//    private Button PersonalbtnEdit;
//    @FXML
//    private Button PersonalbtnSave;
    // ObservableList for TableView data
    private ObservableList<Patient> healthDataList = FXCollections.observableArrayList();
    private User user;
    DBConnect dbConnect = new DBConnect();
    ChartsUtil chartsUtil = new ChartsUtil();

    // Initialize method
    public void initialize(User user) {
        // Set up columns
        boolean PersonalbtnEdit = false;
        this.user = user;
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        sugarLevelColumn.setCellValueFactory(new PropertyValueFactory<>("sugarLevel"));
        heartRateColumn.setCellValueFactory(new PropertyValueFactory<>("heartRate"));
        oxygenLevelColumn.setCellValueFactory(new PropertyValueFactory<>("oxygenLevel"));



        this.user = user;
        dateColumn1.setCellValueFactory(new PropertyValueFactory<>("date"));
        bloodPressureColumn1.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        sugarLevelColumn1.setCellValueFactory(new PropertyValueFactory<>("sugarLevel"));
        heartRateColumn1.setCellValueFactory(new PropertyValueFactory<>("heartRate"));
        oxygenLevelColumn1.setCellValueFactory(new PropertyValueFactory<>("oxygenLevel"));
        //ObservableList<Patient> healthDataList = FXCollections.observableArrayList();

        actionButtons.setCellValueFactory(data -> {
            Patient patient = data.getValue();
            HBox actionButtons = new HBox();
            actionButtons.setSpacing(10);

            // Style buttons
//            updateButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
//            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            // Initially hide or disable the buttons
            updateButton.setVisible(true);
            deleteButton.setVisible(true);

            // Set up the Edit button action
            btnEdit.setOnAction(event -> enableEditing());
            // Set up the Save button action
            btnSave.setOnAction(event -> saveDataToDatabase());
            // Retrieve creationDateTime
            LocalDateTime creationDateTime = patient.getCreationDateTime();
            checkAndDisplayButtons(creationDateTime, updateButton, deleteButton);
            // Update Button Action
            updateButton.setOnAction(event -> {openUpdateDialog(patient, user);});
            // Delete Button Action
            deleteButton.setOnAction(event -> deleteRecord(patient, user));
            actionButtons.getChildren().addAll(updateButton, deleteButton);
            return new SimpleObjectProperty<>(actionButtons);

        });


        actionButtons1.setCellValueFactory(data -> {
            Patient patient = data.getValue();
            HBox actionButtons = new HBox();
            actionButtons.setSpacing(10);

            // Style buttons
//            updateButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
//            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            // Initially hide or disable the buttons
            updateButton.setVisible(true);
            deleteButton.setVisible(true);

            // Set up the Edit button action
            btnEdit.setOnAction(event -> enableEditing());
            // Set up the Save button action
            btnSave.setOnAction(event -> saveDataToDatabase());
            // Retrieve creationDateTime
            LocalDateTime creationDateTime = patient.getCreationDateTime();
            checkAndDisplayButtons(creationDateTime, updateButton, deleteButton);
            // Update Button Action
            updateButton.setOnAction(event -> {openUpdateDialog(patient, user);});
            // Delete Button Action
            deleteButton.setOnAction(event -> deleteRecord(patient, user));
            actionButtons.getChildren().addAll(updateButton, deleteButton);
            return new SimpleObjectProperty<>(actionButtons);

        });
        // Load data from the database
        loadHealthData(user);
        //Personal Info
        // Display only the first 5 records in healthRecordsTable
        if (!healthDataList.isEmpty()) {
            int limit = Math.min(5, healthDataList.size());
            ObservableList<Patient> firstFiveRecords = FXCollections.observableArrayList(healthDataList.subList(0, limit));
            healthRecordsTable.setItems(firstFiveRecords);
        } else {
            healthRecordsTable.setItems(FXCollections.observableArrayList()); // Empty table if no records
        }
        healthRecordsTable1.setItems(healthDataList);
        // For Blood Pressure
        xAxis.setLabel("Date and Time");
        yAxis.setLabel("Blood Pressure");
        yAxis.setAutoRanging(true); // Automatically adjust the Y-axis range
        bloodPressureChart.setAnimated(false); // Disable animation to make data update visible
        // Fetch data and populate the chart
        List<BloodPressureData> data = chartsUtil.fetchBloodPressureData(user.getId());
        chartsUtil.populateChart(data, bloodPressureChart);

        //for Sugar Level
        xAxis_sugarLevel.setLabel("Date and Time");
        yAxis_sugarLevel.setLabel("Sugar Level (mg/dL)");

        // Fetch data and populate the chart
        List<SugarLevelData> sugarLevelData = chartsUtil.fetchSugarLevelData(user.getId());
        chartsUtil.populateChart(sugarLevelData, sugarLevelChart);

        //ForHeartRate
        xAxis.setLabel("Date and Time");
        yAxis.setLabel("Heart Rate (bpm)");

        // Fetch data and populate the chart
        List<HeartRateData> heartRateDatadata = chartsUtil.fetchHeartRateData(user.getId());
        chartsUtil.populateHeartRateChart(heartRateDatadata, heartRateChart);

        //For Oxyge Level
        xAxis.setLabel("Date and Time");
        yAxis.setLabel("Oxygen Level (%)");

        // Fetch data and populate the chart
        List<OxygenLevelData> oxygenLevelDatadata = chartsUtil.fetchOxygenLevelData(user.getId()); // Replace `1` with actual user ID
        chartsUtil.populateOxygenChart(oxygenLevelDatadata,oxygenLevelChart);
        // Ensure the chart is displayed properly
        healthDataChart.setLegendVisible(false); // Hide default legend position

        // Move the legend to the VBox (custom position)
        //customLegend.getChildren().add(healthDataChart.lookup(".chart-legend"));
        //For all together in chart
        xAxis.setLabel("Date and Time");
        yAxis.setLabel("Health Parameters");

        xAxis.setTickLabelRotation(45);
        // Fetch data for all parameters
        List<HealthData> allHealthData = chartsUtil.fetchHealthData(user.getId()); // Replace 1 with the actual user ID
        chartsUtil.populateHealthDataChart(allHealthData,healthDataChart);


    }

    private void saveDataToDatabase() {
        String userID = UserIDField.getText();
        String firstName = FirstNameField.getText();
        String lastName = LastNameField.getText();
        String age = AgeField.getText();
        String phoneNumber = PhoneNumberField.getText();
        String gender = GenderField.getText();
        String email = EmailField.getText();
        String bloodGroup = BloodGroupField.getText();

        // Validate inputs (e.g., non-empty fields)
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || phoneNumber.isEmpty() ||
                gender.isEmpty() || email.isEmpty() || bloodGroup.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All Fields are Required");
            return;
        }

        String updateQuery = "UPDATE Users SET FIRST_NAME = ?, LAST_NAME = ?, AGE = ?, PHONE_NUMBER = ?, GENDER = ?, EMAIL = ?, BLOOD_GROUP = ? WHERE USER_ID = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, age);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, bloodGroup);
            preparedStatement.setString(8, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.ERROR, "Success", "Personal Information Updated");
                // Disable editing
                disableEditing();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void enableEditing() {
        // Enable all TextFields for editing
        FirstNameField.setEditable(true);
        LastNameField.setEditable(true);
        AgeField.setEditable(true);
        PhoneNumberField.setEditable(true);
        GenderField.setEditable(true);
        EmailField.setEditable(true);
        BloodGroupField.setEditable(true);

        // Display a message to the user
        showAlert(Alert.AlertType.ERROR, "INFORMATION", "Now you can Edit Fields");
    }

    private void disableEditing() {
        // Disable all TextFields
        FirstNameField.setEditable(false);
        LastNameField.setEditable(false);
        AgeField.setEditable(false);
        PhoneNumberField.setEditable(false);
        GenderField.setEditable(false);
        EmailField.setEditable(false);
        BloodGroupField.setEditable(false);
    }


    // Function to check and display buttons based on time
    private void checkAndDisplayButtons(LocalDateTime creationDateTime, Button updateButton, Button deleteButton) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long minutesElapsed = Duration.between(creationDateTime, LocalDateTime.now()).toMinutes();
                if (minutesElapsed < 10) {
                    // Show buttons if within 10 minutes
                    Platform.runLater(() -> {
                        updateButton.setVisible(true);
                        deleteButton.setVisible(true);
                    });
                } else {
                    // Hide buttons after 10 minutes
                    Platform.runLater(() -> {
                        updateButton.setDisable(true);
                        deleteButton.setDisable(true);
                    });
                    timer.cancel(); // Stop checking for this row
                }
            }
        }, 0, 1000 * 30); // Check every 30 seconds
    }

    private void openUpdateDialog(Patient healthData, User user) {
        // Open an update dialog
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/UpdateHealthDataPatient.fxml"));
            Parent root = loader.load();
            UpdatePatientHealthDataController updateController = loader.getController();
            updateController.setInitialData(healthData, user);

            // Create a new stage
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Health Data");
            updateStage.setScene(new Scene(root));
            updateStage.showAndWait();
            healthDataList.clear();
            loadHealthData(user);
            //healthRecordsTable.refresh(); // Refresh table to reflect updated data
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open the Update window.");


        }

    }

    private void refreshData(Patient healthData) {
    }

    @FXML
    public void filterData(ActionEvent event) {
        // Get the selected dates from the DatePickers
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        // Validate the selected date range
        if (fromDate == null || toDate == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select both From and To dates.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "From date cannot be after To date.");
            return;
        }

        try {
            // Retrieve filtered data
            PatientHealthDataManager manager = new PatientHealthDataManager();
            healthDataList = manager.getHealthDataInRange(fromDate, toDate);

            healthRecordsTable1.setItems(healthDataList);
            // Process or display the filtered data
           //] displayData(filteredData);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while filtering data.");
        }
    }



    public List<PatientHealthData> getHealthDataInRange(LocalDate fromDate, LocalDate toDate) throws SQLException {
        List<PatientHealthData> healthDataList = new ArrayList<>();

        String query = "SELECT * FROM patient_health_data WHERE date BETWEEN ? AND ?";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, java.sql.Date.valueOf(fromDate));
            statement.setDate(2, java.sql.Date.valueOf(toDate));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Create PatientHealthData objects from the result set
                PatientHealthData healthData = new PatientHealthData(
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("blood_pressure"),
                        resultSet.getString("sugar_level"),
                        resultSet.getInt("heart_rate"),
                        resultSet.getInt("oxygen_level"),
                        resultSet.getString("comments"),
                        resultSet.getTimestamp("creation_date_time").toLocalDateTime()
                );
                healthDataList.add(healthData);
            }
        }

        return healthDataList;
    }



    private void updateHealthDataInDatabase(Patient healthData) {
        String query = "UPDATE HealthData SET blood_pressure = ?, sugar_level = ?, heart_rate = ?, oxygen_level = ? WHERE user_id = ? AND data_date = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, healthData.getBloodPressure());
            preparedStatement.setString(2, healthData.getSugarLevel());
            preparedStatement.setInt(3, healthData.getHeartRate());
            preparedStatement.setInt(4, healthData.getOxygenLevel());
            preparedStatement.setString(5, healthData.getUserId());
            preparedStatement.setDate(6, java.sql.Date.valueOf(healthData.getDate()));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("Failed to update record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void deleteRecord(Patient healthData, User user) {
        String query = "DELETE FROM HealthData WHERE user_id = ? AND data_date = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getId());
            preparedStatement.setDate(2, java.sql.Date.valueOf(healthData.getDate()));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.ERROR, "Success", "Health record deleted successfully.");
                healthDataList.remove(healthData); // Remove record from UI


            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the health record. Record may not exist.");
                System.out.println("Failed to delete record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    private void addDeleteButtonToTable(){
//        columnDelete.setCellFactory(param ->new TableCell<>()
//
//        {
//            private final Button deleteButton = new Button("Delete");
//
//            {
//                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
//                deleteButton.setOnAction(event -> {
//                    Patient data = getTableView().getItems().get(getIndex());
//                    deleteHealthData(data);
//                });
//            }
//
//            @Override
//            protected void updateItem (Void item,boolean empty){
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(deleteButton);
//                }
//            }
//        });
//    }

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
                showAlert(Alert.AlertType.ERROR, "Success", "Health record deleted successfully.");
                healthRecordsTable.getItems().remove(healthData); // Remove from TableView
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the health record. Record may not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the health record.");
        }
    }


    private void showAlert(Alert.AlertType error, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


//    private void loadHealthData(User user) {
//        healthDataList.clear();
//
//        String dbUrl = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
//        String dbUser = "n01660845";
//        String dbPassword = "oracle";
//        String userID = user.getId();
//        String query = "SELECT USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS, CREATIONDATETIME FROM HealthData WHERE USER_ID = ?";
//        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
//            // Replace with the actual user ID you want to filter by
//            preparedStatement.setString(1, userID);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//
//                // Handle null dates
//                LocalDate dataDate = null;
//                if (resultSet.getDate("DATA_DATE") != null) {
//                    dataDate = resultSet.getDate("DATA_DATE").toLocalDate();
//                }
//                Timestamp creationTimestamp = resultSet.getTimestamp("creationDateTime");
//                LocalDateTime creationDateTime = creationTimestamp.toLocalDateTime();
//
//
//                Patient healthData = new Patient(
//                        dataDate,
//                        resultSet.getString("blood_pressure"),
//                        resultSet.getString("sugar_level"),
//                        resultSet.getInt("heart_rate"),
//                        resultSet.getInt("oxygen_level"),
//                        resultSet.getString("comments"),
//                        creationDateTime);
//
//                // Add each record to the ObservableList
//
//                healthDataList.add(healthData);
//            }
//
//            // Set the data in the TableView
//            healthRecordsTable.setItems(healthDataList);
//            healthRecordsTable1.setItems(healthDataList);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    private void loadHealthData(User user) {
        healthDataList.clear();

        String dbUrl = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
        String dbUser = "n01660845";
        String dbPassword = "oracle";
        String userID = user.getId();

        // Updated query with ORDER BY clause
        String query = "SELECT USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS, CREATIONDATETIME " +
                "FROM HealthData " +
                "WHERE USER_ID = ? " +
                "ORDER BY creationDateTime DESC";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the user ID in the prepared statement
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Handle null dates
                LocalDate dataDate = null;
                if (resultSet.getDate("DATA_DATE") != null) {
                    dataDate = resultSet.getDate("DATA_DATE").toLocalDate();
                }

                // Convert Timestamp to LocalDateTime
                Timestamp creationTimestamp = resultSet.getTimestamp("creationDateTime");
                LocalDateTime creationDateTime = creationTimestamp.toLocalDateTime();

                // Create a Patient object
                Patient healthData = new Patient(
                        dataDate,
                        resultSet.getString("blood_pressure"),
                        resultSet.getString("sugar_level"),
                        resultSet.getInt("heart_rate"),
                        resultSet.getInt("oxygen_level"),
                        resultSet.getString("comments"),
                        creationDateTime
                );

                // Add each record to the ObservableList
                healthDataList.add(healthData);
            }

            // Set the data in the TableView
            // Display only the first 5 records in healthRecordsTable
            if (!healthDataList.isEmpty()) {
                int limit = Math.min(5, healthDataList.size());
                ObservableList<Patient> firstFiveRecords = FXCollections.observableArrayList(healthDataList.subList(0, limit));
                healthRecordsTable.setItems(firstFiveRecords);
            } else {
                healthRecordsTable.setItems(FXCollections.observableArrayList()); // Empty table if no records
            }
            healthRecordsTable1.setItems(healthDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    UserSession userSession;

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

        FXMLLoader fxmlLoader = new FXMLLoader(PatientDashboardController.class.getResource("/com/zodiac/homehealthdevicedatalogger/Views/AddHealthData.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Health Data");
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

    public void handleRefresh(ActionEvent actionEvent) {
        // Call loadHealthData to refresh the table
        if (user != null) {
            loadHealthData(user);
            showAlert(Alert.AlertType.ERROR, "Success", "Health data refreshed successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to refresh. User information is missing.");
        }
    }


    public void loadDataPatientDetail(User user) {

        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER, EMAIL, BLOOD_GROUP FROM USERS WHERE USER_ID = ? AND ROLE_NAME = 'Patient'";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getId()); // Replace with dynamic ID as needed
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserIDField.setText(rs.getString("USER_ID"));
                FirstNameField.setText(rs.getString("FIRST_NAME"));
                LastNameField.setText(rs.getString("LAST_NAME"));
                AgeField.setText(rs.getString("AGE"));
                PhoneNumberField.setText(rs.getString("PHONE_NUMBER"));
                GenderField.setText(rs.getString("GENDER"));
                EmailField.setText(rs.getString("EMAIL"));
                BloodGroupField.setText(rs.getString("BLOOD_GROUP"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private List<BloodPressureData> fetchBloodPressureData(String userId) {
//        List<BloodPressureData> readings = new ArrayList<>();
//
//        String query = "SELECT BLOOD_PRESSURE, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";
//
//        try (Connection connection = dbConnect.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, userId); // Set the user ID
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String bloodPressure = resultSet.getString("BLOOD_PRESSURE");
//                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();
//
//                if (bloodPressure != null && bloodPressure.contains("/")) {
//                    String[] parts = bloodPressure.split("/");
//                    int systolic = Integer.parseInt(parts[0]);
//                    int diastolic = Integer.parseInt(parts[1]);
//
//                    readings.add(new BloodPressureData(creationDateTime, systolic, diastolic));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return readings;
//    }
//
//    private void populateChart(List<BloodPressureData> data) {
//        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
//        systolicSeries.setName("Systolic");
//
//        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
//        diastolicSeries.setName("Diastolic");
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//        // Add data points to the series
//        for (BloodPressureData reading : data) {
//            String formattedDate = reading.getDateTime().format(formatter);
//            System.out.println("Adding data point: " + formattedDate + " - Systolic: " + reading.getSystolic() + ", Diastolic: " + reading.getDiastolic());
//
//            systolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSystolic()));
//            diastolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getDiastolic()));
//        }
//
//        // Check if series contains data
//        if (systolicSeries.getData().isEmpty() && diastolicSeries.getData().isEmpty()) {
//            System.out.println("No data points were added to the series!");
//        }
//
//        // Add series to the chart
//        bloodPressureChart.getData().clear(); // Clear existing data if any
//        bloodPressureChart.getData().addAll(systolicSeries, diastolicSeries);
//    }


    }
