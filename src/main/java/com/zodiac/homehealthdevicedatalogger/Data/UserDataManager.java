package com.zodiac.homehealthdevicedatalogger.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.zodiac.homehealthdevicedatalogger.Models.User;

public class UserDataManager {
    private static final String FILE_PATH = "UserData.json";
    String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    String username = "n01660845";
    String password = "oracle";
    // Save a user to the file by appending to the existing list
//    public void saveUser(User user) {
//        List<User> users = getAllUsers();
//        users.add(user);
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            mapper.writeValue(new File(FILE_PATH), users);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void saveUser(User user) throws SQLException, IOException {
        String sql = "INSERT INTO USERS (USER_ID, FIRST_NAME, LAST_NAME, AGE, PHONE_NUMBER, GENDER,  EMAIL, PASSWORD ,ROLE_ID , ROLE_NAME) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";

        try (Connection connection = DriverManager.getConnection(url, username , password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println("Connected to DB");

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
            System.out.println("Save to DB"+ statement.toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all users from the JSON file
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";

        try (Connection connection = DriverManager.getConnection(url, username , password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("FIRST_NAME"));
                user.setLastName(resultSet.getString("LAST_NAME"));
                user.setAge(resultSet.getInt("AGE"));
                user.setPhone(resultSet.getString("PHONE"));
                user.setGender(resultSet.getString("GENDER"));
                user.setRole(resultSet.getString("ROLE"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setConfirmPassword(resultSet.getString("CONFIRM_PASSWORD"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}