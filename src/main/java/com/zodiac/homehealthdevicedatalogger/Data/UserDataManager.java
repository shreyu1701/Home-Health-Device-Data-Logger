package com.zodiac.homehealthdevicedatalogger.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;

public class UserDataManager {


	public void saveUser(User user) throws IOException {
		String sql = "INSERT INTO USERS (USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER,  EMAIL, PASSWORD ,ROLE_ID , ROLE_NAME, BLOOD_GROUP) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?)";

		try (Connection connection = DBConnect.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, user.getId());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setInt(4, user.getAge());
			statement.setString(5, user.getPhone());
			statement.setString(6, user.getGender());
			statement.setString(7, user.getEmail());
			statement.setString(8, user.getPassword());
			statement.setString(9, String.valueOf(user.getRoleID()));
			statement.setString(10, String.valueOf(user.getRole()));
			statement.setString(11, String.valueOf(user.getBloodGroup()));

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get all users from database
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM USERS";

		try (Connection connection = DBConnect.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getString("USER_ID"));
				user.setFirstName(resultSet.getString("FIRST_NAME"));
				user.setLastName(resultSet.getString("LAST_NAME"));
				user.setAge(resultSet.getInt("AGE"));
				user.setPhone(resultSet.getString("PHONE_NUMBER"));
				user.setGender(resultSet.getString("GENDER"));
				user.setRole(resultSet.getString("ROLE_NAME"));
				user.setEmail(resultSet.getString("EMAIL"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setConfirmPassword(resultSet.getString("PASSWORD"));
				user.setRoleID(resultSet.getString("ROLE_ID"));
				user.setBloodGroup(resultSet.getString("BLOOD_GROUP"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void getLoggedInUser(User user) throws SQLException {
		String sql = "SELECT USERS (USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER,  EMAIL,ROLE_ID , ROLE_NAME, BLOOD_GROUP) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?)";

		try (Connection connection = DBConnect.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

		}
	}

	public static List<String> getAllUserIds() {
		List<String> userIds = new ArrayList<>();

		String query = "SELECT UserID FROM Users";

		try (Connection connection = DBConnect.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {

			// Process the result set to extract UserID values
			while (resultSet.next()) {
				userIds.add(resultSet.getString("User_ID")); // Add UserID to the list
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle any SQL exceptions
		}

		return userIds;
	}
}