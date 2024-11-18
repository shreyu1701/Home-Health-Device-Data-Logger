package com.zodiac.homehealthdevicedatalogger.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PatientHealthDataManager {

	private static final String FILE_PATH = "PatientHealthData.json";
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.registerModule(new JavaTimeModule());
	}
	// Method to save health data to the JSON file
	public void saveHealthData(List<PatientHealthData> healthDataList) {
		try {
			// Write the list of health data to the file
			mapper.writeValue(new File(FILE_PATH), healthDataList);
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the error (e.g., show an alert or log it)
		}
	}

	// Method to retrieve all health data from the JSON file
	public List<PatientHealthData> getAllHealthData() {
		try {
			File file = new File(FILE_PATH);
			if (file.exists()) {
				return mapper.readValue(new File(FILE_PATH), new TypeReference<List<PatientHealthData>>(){});
//				return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, PatientHealthData.class));
			} else {
				return null; // No data file exists
			}
			// Read the JSON file and map it to a list of PatientHealthData objects
		} catch (IOException e) {
			// Print error message if reading from file fails
			System.err.println("Error reading from file: " + e.getMessage());
			return null;
		}
	}



	// Method to add a new health record to the existing list of health data
	public void addHealthData(PatientHealthData newHealthData) {
		List<PatientHealthData> currentData = getAllHealthData();

		if (currentData != null) {
			currentData.add(newHealthData);
		} else {
			currentData = List.of(newHealthData); // If no data exists, create a new list with the new entry
		}

		// Save the updated list back to the JSON file
		saveHealthData(currentData);
	}
}
