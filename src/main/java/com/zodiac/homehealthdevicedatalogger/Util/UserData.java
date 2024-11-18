package com.zodiac.homehealthdevicedatalogger.Util;

// Define UserData class to wrap the list of users

import com.zodiac.homehealthdevicedatalogger.Models.User;

import java.util.List;

public class UserData {
    private List<User> users;

    // Default constructor
    public UserData() {}

    // Getter and Setter
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}