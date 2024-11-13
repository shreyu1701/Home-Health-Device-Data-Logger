package com.zodiac.homehealthdevicedatalogger.Util;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class PasswordRetrieverJson {

    public static String getPasswordForEmail(String targetEmail) {

        String filePath = "UserData.json"; // Path to your JSON file
        //String targetEmail = email; // Change this to search for a different email
        String password = null;
        try (FileReader reader = new FileReader(filePath)) {
            // Parse JSON file into JsonArray
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            // Loop through each JSON object in the array
            for (JsonElement element : jsonArray) {
                JsonObject userObject = element.getAsJsonObject();
                String fileEmail = userObject.get("email").getAsString();
                if (fileEmail.equals(targetEmail)) {
                    password = userObject.get("password").getAsString();
//                    System.out.println("Password for email " + targetEmail + ": " + password);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
