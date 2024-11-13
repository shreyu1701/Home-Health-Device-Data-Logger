package com.zodiac.homehealthdevicedatalogger.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zodiac.homehealthdevicedatalogger.Models.User;

public class UserDataManager {
    private static final String FILE_PATH = "UserData.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Save a user to the file by appending to the existing list
    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all users from the JSON file
    public List<User> getAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(FILE_PATH), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
