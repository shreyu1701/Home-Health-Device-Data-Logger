package com.zodiac.homehealthdevicedatalogger.Validation;

import com.zodiac.homehealthdevicedatalogger.Data.UserDataManager;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import com.zodiac.homehealthdevicedatalogger.Util.PasswordUtils;

import java.io.IOException;
import java.util.List;



public class Validator {

  //  UserDataManager userDataManager = new UserDataManager();

//    public User validateUser(String email, String password) throws IOException {
//        List<User> users = userDataManager.getAllUsers();
//
//        for (User user : users) {
//            if (user.getEmail().equals(email)) {
//                // Extract salt and stored password
//                String[] parts = user.getPassword().split(":");
//                if (parts.length == 2) {
//                    String storedSalt = parts[0];
//                    String storedHashedPassword = parts[1];
//
//                    // Validate password by hashing the entered password with the stored salt
//                    if (PasswordUtils.validatePassword(password, storedSalt + ":" + storedHashedPassword)) {
//                        return user; // Valid user and password
//                    }
//                }
//            }
//        }
//
//        return null; // User not found or password incorrect
//    }


    // Helper method to validate email
    public boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailPattern);
    }


//
//    // Helper method to validate name
//    public boolean isValidName(String name) {
//        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
//    }
//
//    // Helper method to validate age (must be a number)
//    public boolean isValidAge(int age) {
//        try {
//            int ageNumber = age;
//            return ageNumber > 0;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    // Helper method to validate phone number (basic pattern)
//    public boolean isValidPhoneNumber(String phone) {
//        return phone != null && phone.matches("\\d{10}");
//    }
//
//
//    // Helper method to validate password
//    public String isValidPassword(String password) {
//
//        if (password == null || password.length() < 8) {
//            return "Password must be at least 8 characters long.";
//        }
//
//        // Regular expressions for required conditions
//        String upperCasePattern = ".*[A-Z].*";
//        String lowerCasePattern = ".*[a-z].*";
//        String digitPattern = ".*[0-9].*";
//        String specialCharPattern = ".*[!@#$%^&*()\\-+].*";
//
//        // Check if password matches each condition
//        if (!password.matches(upperCasePattern)) {
//            return "Enter atleast 1 Uppercase Letter"; // No uppercase letter
//        }
//
//        if (!password.matches(lowerCasePattern)) {
//            return "Enter Atleast 1 lowercase Letter"; // No lowercase letter
//        }
//
//        if (!password.matches(digitPattern)) {
//            return "Enter atleast 1 digit"; // No digit
//        }
//
//        if (!password.matches(specialCharPattern)) {
//            return "Enter atleast 1 special character"; // No special character
//        }
//
//        // All conditions met
//        return "";


//        return password != null && password.length() >= 8;
  //  }//


}
