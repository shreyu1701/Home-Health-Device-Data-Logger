package com.zodiac.homehealthdevicedatalogger.Util;
import java.io.*;
import java.util.Scanner;

public class IDGenerator {

    private static final String PATIENT_ID_PREFIX = "P";
    private static final String TECHNICIAN_ID_PREFIX = "T";
    private static final String ID_FILE = "last_used_id.txt";  // File to store the last used ID

    // Method to generate a new Patient or Technician ID
    public String generateNewId(String role) {
        int lastUsedId = getLastUsedId();  // Get the last used ID
        lastUsedId++;  // Increment the ID

        // Save the updated ID to the file
        saveLastUsedId(lastUsedId);

        // Create the ID based on the role (Patient or Technician)
        String prefix = role.equalsIgnoreCase("Patient") ? PATIENT_ID_PREFIX : TECHNICIAN_ID_PREFIX;
        return prefix + String.format("%04d", lastUsedId);  // Format ID as P0001, T0001, etc.
    }

    // Method to get the last used ID from the file
    private static int getLastUsedId() {
        int lastUsedId = 0;
        try {
            File file = new File(ID_FILE);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextInt()) {
                    lastUsedId = scanner.nextInt();  // Read the last used ID from the file
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastUsedId;
    }

    // Method to save the updated last used ID to the file
    private static void saveLastUsedId(int id) {
        try {
            FileWriter writer = new FileWriter(ID_FILE);
            writer.write(String.valueOf(id));  // Save the new ID to the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        // Test ID generation for Patient and Technician
//        String patientId = generateNewId("Patient");
//        String technicianId = generateNewId("Technician");
//
//        System.out.println("New Patient ID: " + patientId);  // Output: P0001, P0002, etc.
//        System.out.println("New Technician ID: " + technicianId);  // Output: T0001, T0002, etc.
//    }
}
