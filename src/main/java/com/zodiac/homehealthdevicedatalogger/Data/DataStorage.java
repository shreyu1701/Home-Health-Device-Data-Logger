package com.zodiac.homehealthdevicedatalogger.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zodiac.homehealthdevicedatalogger.Models.User;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    DBConnect dbConnect = new DBConnect();

    private static final String FILE_PATH = "PatientHealthData.json";

    // Method to save health data
    public static void saveHealthData(PatientHealthData data) throws IOException {
        List<PatientHealthData> healthDataList = new ArrayList<>();

        // Check if the file exists and if data already exists
        File file = new File(FILE_PATH);
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            List<PatientHealthData> existingData = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, PatientHealthData.class));
            healthDataList.addAll(existingData); // Add existing data
        }

        // Add the new data
        healthDataList.add(data);

        // Serialize data to JSON and write to file
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Format JSON
        mapper.writeValue(file, healthDataList);
    }


    public User loadUserDetailforReport(String userID) {
        User user = null;

        // SQL query to fetch user details by email
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER, EMAIL, BLOOD_GROUP, ROLE_NAME " +
                "FROM USERS WHERE USER_ID = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the email parameter in the query
            stmt.setString(1, userID);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create a new User object and populate it with database data
                    user = new User();
                    user.setId(rs.getString("USER_ID"));
                    user.setFirstName(rs.getString("FIRST_NAME"));
                    user.setLastName(rs.getString("LAST_NAME"));
                    user.setAge(rs.getInt("AGE")); // Use getInt to handle numeric values
                    user.setPhone(rs.getString("PHONE_NUMBER"));
                    user.setGender(rs.getString("GENDER"));
                    user.setEmail(rs.getString("EMAIL"));
                    user.setBloodGroup(rs.getString("BLOOD_GROUP"));
                    user.setRole(rs.getString("ROLE_NAME")); // Assuming User has a setRole method
                } else {
                    System.out.println("No user found with the provided User Id : " + userID);
                }
            }

        } catch (SQLException e) {
            // Log detailed database errors
            System.err.println("Database error while loading user details: " + e.getMessage());
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return user;

    }



    public User loadUserDetail(String email) {
        User user = null;

        // SQL query to fetch user details by email
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER, EMAIL, BLOOD_GROUP, ROLE_NAME " +
                "FROM USERS WHERE EMAIL = ?";

        try (Connection conn = dbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the email parameter in the query
            stmt.setString(1, email);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create a new User object and populate it with database data
                    user = new User();
                    user.setId(rs.getString("USER_ID"));
                    user.setFirstName(rs.getString("FIRST_NAME"));
                    user.setLastName(rs.getString("LAST_NAME"));
                    user.setAge(rs.getInt("AGE")); // Use getInt to handle numeric values
                    user.setPhone(rs.getString("PHONE_NUMBER"));
                    user.setGender(rs.getString("GENDER"));
                    user.setEmail(rs.getString("EMAIL"));
                    user.setBloodGroup(rs.getString("BLOOD_GROUP"));
                    user.setRole(rs.getString("ROLE_NAME")); // Assuming User has a setRole method
                } else {
                    System.out.println("No user found with the provided Email Id: " + email);
                }
            }

        } catch (SQLException e) {
            // Log detailed database errors
            System.err.println("Database error while loading user details: " + e.getMessage());
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return user;

    }
}