package com.zodiac.homehealthdevicedatalogger.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {

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
}