package com.zodiac.homehealthdevicedatalogger.Util;

import com.zodiac.homehealthdevicedatalogger.Models.User;

import java.util.List;

public class Util {


    User user;
    // Method to find user by ID
    public User findUserById(List<User> users, String userId) {
        for (User user : users) {
            if (String.valueOf(user.getId()).equals(userId)) {
                return user;
            }
        }
        return null; // User not found
    }


    public String getId() {
        return "UID_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);
    }
}
