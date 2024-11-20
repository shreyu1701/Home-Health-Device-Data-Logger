package com.zodiac.homehealthdevicedatalogger.Controllers;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
//import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.zodiac.homehealthdevicedatalogger.Controllers.LoginController.GUILoader;
import static com.zodiac.homehealthdevicedatalogger.Controllers.PatientDashboardController.logout;

public class TechnicianDashboardController {

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
	private TableColumn<PatientHealthData, Void> updateColumn;
	@FXML
	private TableColumn<PatientHealthData, Void> columnDelete;


	public void initialize() {
		// Set up columns to bind to PatientHealthData properties
		columnDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
		columnUser.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserId()));
		columnBloodPressure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBloodPressure()));
		columnSugar.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSugarLevel()));
		columnHeartRate.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getHeartRate()).asObject());
		columnOxygen.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOxygenLevel()).asObject());

		// Load data from the database
		loadAllHealthData();
		// Add delete button column
		addDeleteButtonToTable();

		// Add Update button column
//        updateColumn.setCellFactory(param -> new TableCell<>() {
//            private final Button updateButton = new Button("Update");
//
//            {
//                updateButton.setOnAction(event -> {
//                    PatientHealthData selectedData = getTableRow().getItem();
//                    if (selectedData != null) {
//                        openUpdateDialog(selectedData);
//                    }
//                });
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(updateButton);
//                }
//            }
//        });
	}

	private void addDeleteButtonToTable() {
		columnDelete.setCellFactory(param -> new TableCell<>() {
			private final Button deleteButton = new Button("Delete");

			{
				deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
				deleteButton.setOnAction(event -> {
					PatientHealthData data = getTableView().getItems().get(getIndex());
					deleteHealthData(data);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(deleteButton);
				}
			}
		});
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

	public void loadAllHealthData() {
		String query = "SELECT user_id, data_date, blood_pressure, sugar_level, heart_rate, oxygen_level, comments FROM HealthData";

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
						resultSet.getString("comments")
				);
				healthDataList.add(healthData);
			}

			// Bind the data to the TableView
			healthRecordsTable.setItems(healthDataList);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}


	public void handleTechnicianLogout(ActionEvent actionEvent) throws IOException {
		logout(btnTechnicianLogout);
	}

//    public void handleAddPatient(ActionEvent actionEvent) {
//        System.out.println("Add Patient");
//    }
}
