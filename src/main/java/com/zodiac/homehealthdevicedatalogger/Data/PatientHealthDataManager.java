package com.zodiac.homehealthdevicedatalogger.Data;

import com.zodiac.homehealthdevicedatalogger.Models.HealthData;
import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientHealthDataManager {

//	private static final String FILE_PATH = "PatientHealthData.json";
//	private static final ObjectMapper mapper = new ObjectMapper();
//
//	static {
//		mapper.registerModule(new JavaTimeModule());
//	}
	// Method to save health data to the Database
public void saveHealthData(PatientHealthData healthData, User currentUser) throws SQLException {
	// SQL Insert Query

	String insertQuery = "INSERT INTO HealthData (USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS, CREATIONDATETIME) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	// Establish database connection
	try (Connection connection = DBConnect.getConnection();
		 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

		// Set parameters for the PreparedStatement
		preparedStatement.setString(1, currentUser.getId()); // User ID from the healthData object
		preparedStatement.setDate(2, java.sql.Date.valueOf(healthData.getDate())); // Convert LocalDate to sql.Date
		preparedStatement.setString(3, healthData.getBloodPressure());
		preparedStatement.setString(4, healthData.getSugarLevel());

		// Ensure heart rate and oxygen level are integers
		preparedStatement.setInt(5, healthData.getHeartRate());
		preparedStatement.setInt(6, healthData.getOxygenLevel());

		preparedStatement.setString(7, healthData.getComment());
		preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(healthData.getCreationDateTime()));

		// Execute the insert statement
		int rowsInserted = preparedStatement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("Data saved successfully to the database.");
		} else {
			System.out.println("Failed to save data to the database.");
		}

	} catch (SQLException e) {
		// Log the exception with a meaningful message
		System.err.println("Error saving health data: " + e.getMessage());
		e.printStackTrace();
		// Optionally, rethrow the exception if you want to handle it elsewhere
		throw new SQLException("Error saving health data", e);
	}
}






//	public void saveHealthData(PatientHealthData healthData) throws SQLException {
//
//		String insertQuery = "INSERT INTO Patient (DATA_ID, USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS) " +
//				"VALUES (SEQ_DATA_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
//
//		// Establish database connection
//			Connection connection =DBConnect.getConnection();
//			if (null != connection) {
//				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);) {
//
//					// Set parameters
//					preparedStatement.setInt(1, 1);
//					preparedStatement.setDate(2, java.sql.Date.valueOf(healthData.getDate()));
//					preparedStatement.setString(3, healthData.getBloodPressure());
//					preparedStatement.setString(4, healthData.getSugarLevel());
//					preparedStatement.setString(5, healthData.getHeartRate());
//					preparedStatement.setString(6, healthData.getOxygenLevel());
//					preparedStatement.setString(7, healthData.getComment());
//
//					// Execute the insert statement
//					preparedStatement.executeUpdate();
//			}
//
//		}



//		try {
//			// Write the list of health data to the file
//			mapper.writeValue(new File(FILE_PATH), healthDataList);
//		} catch (IOException e) {
//			e.printStackTrace();
//			// Handle the error (e.g., show an alert or log it)
//		}


	// Method to retrieve all health data from the JSON file
//	public List<PatientHealthData> getAllHealthData() {
//		try {
//			File file = new File(FILE_PATH);
//			if (file.exists()) {
//				return mapper.readValue(new File(FILE_PATH), new TypeReference<List<PatientHealthData>>(){});
////				return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, PatientHealthData.class));
//			} else {
//				return null; // No data file exists
//			}
//			// Read the JSON file and map it to a list of PatientHealthData objects
//		} catch (IOException e) {
//			// Print error message if reading from file fails
//			System.err.println("Error reading from file: " + e.getMessage());
//			return null;
//		}
//	}



	// Method to add a new health record to the existing list of health data
	public void addHealthData(PatientHealthData newHealthData, User currentUser) throws SQLException {

		saveHealthData(newHealthData, currentUser );
	}

	public ObservableList<Patient> getHealthDataInRange(LocalDate fromDate, LocalDate toDate) throws SQLException {
	ObservableList<Patient> healthDataList = FXCollections.observableArrayList();
		String query = "SELECT * FROM HealthData WHERE DATA_DATE BETWEEN ? AND ?";
		try (Connection connection = DBConnect.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, java.sql.Date.valueOf(fromDate));
			statement.setDate(2, java.sql.Date.valueOf(toDate));

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// Create PatientHealthData objects from the result set
				Patient healthData = new Patient(
						resultSet.getDate("DATA_DATE").toLocalDate(),
						resultSet.getString("blood_pressure"),
						resultSet.getString("sugar_level"),
						resultSet.getInt("heart_rate"),
						resultSet.getInt("oxygen_level"),
						resultSet.getString("comments"),
						resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime()
				);
				healthDataList.add(healthData);
			}
		}

		return healthDataList;
	}

	public static List<PatientHealthData> getHealthDataInRangeReport(LocalDate fromDate, LocalDate toDate) throws SQLException {
		List<PatientHealthData> healthDataList = new ArrayList<>();

		String query = "SELECT * FROM HEALTHDATA WHERE Data_Date BETWEEN ? AND ?";
		try (Connection connection = DBConnect.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, java.sql.Date.valueOf(fromDate));
			statement.setDate(2, java.sql.Date.valueOf(toDate));

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// Create PatientHealthData objects from the result set
				PatientHealthData healthData = new PatientHealthData(
						resultSet.getDate("Data_Date").toLocalDate(),
						resultSet.getString("blood_pressure"),
						resultSet.getString("sugar_level"),
						resultSet.getInt("heart_rate"),
						resultSet.getInt("oxygen_level"),
						resultSet.getString("comments"),
						resultSet.getTimestamp("creationdatetime").toLocalDateTime()
				);
				healthDataList.add(healthData);
			}
		}

		return healthDataList;
	}



}
