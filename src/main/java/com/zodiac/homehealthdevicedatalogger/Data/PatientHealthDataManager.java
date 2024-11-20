package com.zodiac.homehealthdevicedatalogger.Data;

import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	String insertQuery = "INSERT INTO HealthData (USER_ID, DATA_DATE, BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, COMMENTS) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";

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
}
