package com.zodiac.homehealthdevicedatalogger.Data;

import com.zodiac.homehealthdevicedatalogger.Models.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthDataAccess {

	public static List<Patient> getHealthDataByUserId(String userId) throws SQLException {
		String query = "SELECT * FROM HealthData WHERE user_id = ?";
		try (Connection connection = DBConnect.getConnection()) {
			if (connection == null) {
				throw new SQLException("Failed to establish database connection.");
			}
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, userId);
				ResultSet resultSet = statement.executeQuery();
				List<Patient> dataList = new ArrayList<>();
				while (resultSet.next()) {
					Patient data = new Patient(
							resultSet.getDate("data_date").toLocalDate(),
							resultSet.getString("blood_pressure"),
							resultSet.getString("sugar_level"),
							resultSet.getInt("heart_rate"),
							resultSet.getInt("oxygen_level"),
							resultSet.getString("comments"),
							resultSet.getTimestamp("creationdatetime").toLocalDateTime()
					);
					data.setUserId(resultSet.getString("user_Id"));
					dataList.add(data);
				}
				return dataList;
			}
		}
	}

	public static List<Patient> getHealthDataForDateRange(String userId, LocalDate fromDate, LocalDate toDate) {
		List<Patient> healthDataList = new ArrayList<>();

		String query = "SELECT data_date, blood_pressure, sugar_level, heart_rate, oxygen_level, comments , creationdatetime " +
				"FROM HealthData " +
				"WHERE user_id = ? AND data_date BETWEEN ? AND ?";

		try (Connection conn = DBConnect.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			java.sql.Date sqlFromDate = java.sql.Date.valueOf(fromDate);
			java.sql.Date sqlToDate = java.sql.Date.valueOf(toDate);
			stmt.setString(1, userId); // Set the user ID
			stmt.setDate(2, sqlFromDate); // Set the 'from' date
			stmt.setDate(3, sqlToDate);   // Set the 'to' date


			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Patient healthData = new Patient(
						rs.getDate("data_date").toLocalDate(),
						rs.getString("blood_pressure"),
						rs.getString("sugar_level"),
						rs.getInt("heart_rate"),
						rs.getInt("oxygen_level"),
						rs.getString("comments"),
						rs.getTimestamp("creationdatetime").toLocalDateTime()
				);
				healthDataList.add(healthData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return healthDataList;
	}

}
