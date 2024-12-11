package com.zodiac.homehealthdevicedatalogger.Util;

import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
import com.zodiac.homehealthdevicedatalogger.Models.*;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChartsUtil {
    DBConnect dbConnect = new DBConnect();


    public List<BloodPressureData> fetchBloodPressureData(String userId) {
        List<BloodPressureData> readings = new ArrayList<>();

        String query = "SELECT BLOOD_PRESSURE, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userId); // Set the user ID
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bloodPressure = resultSet.getString("BLOOD_PRESSURE");
                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();

                if (bloodPressure != null && bloodPressure.contains("/")) {
                    String[] parts = bloodPressure.split("/");
                    int systolic = Integer.parseInt(parts[0]);
                    int diastolic = Integer.parseInt(parts[1]);

                    readings.add(new BloodPressureData(creationDateTime, systolic, diastolic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readings;
    }

    public void populateChart(List<BloodPressureData> data, LineChart<String, Number> bloodPressureChart) {
        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Systolic");

        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolic");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Add data points to the series
        for (BloodPressureData reading : data) {

            Integer systolic = reading.getSystolic();
            Integer diastolic = reading.getDiastolic();

            if (systolic == null || diastolic == null) {
//                System.out.println("Skipping data point for date: " + reading.getDateTime() + " because of null value(s).");
                continue;
            }
            String formattedDate = reading.getDateTime().format(formatter);
//            System.out.println("Adding data point: " + formattedDate + " - Systolic: " + reading.getSystolic() + ", Diastolic: " + reading.getDiastolic());

            systolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSystolic()));
            diastolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getDiastolic()));
        }

        // Check if series contains data
        if (systolicSeries.getData().isEmpty() && diastolicSeries.getData().isEmpty()) {
            System.out.println("No data points were added to the series!");
        }

        // Add series to the chart
        bloodPressureChart.getData().clear(); // Clear existing data if any
        bloodPressureChart.getData().addAll(systolicSeries, diastolicSeries);
    }



    public List<SugarLevelData> fetchSugarLevelData(String userId) {
        List<SugarLevelData> readings = new ArrayList<>();
        String query = "SELECT SUGAR_LEVEL, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int sugarLevel = resultSet.getInt("SUGAR_LEVEL");
                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();

                readings.add(new SugarLevelData(creationDateTime, sugarLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readings;
    }

    public void populateChart(List<SugarLevelData> data, AreaChart<String, Number> sugarLevelChart) {
        XYChart.Series<String, Number> sugarSeries = new XYChart.Series<>();
        sugarSeries.setName("Sugar Level");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (SugarLevelData reading : data) {

            if (reading.getSugarLevel() == 0) {
//                System.out.println("Skipping data point for date: " + reading.getDateTime() + " because sugar level is 0.");
                continue; // Skip this record
            }
            String formattedDate = reading.getDateTime().format(formatter);
//            System.out.println("Adding data point: " + formattedDate + ", Sugar Level: " + reading.getSugarLevel());
            sugarSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSugarLevel()));
        }

        sugarLevelChart.getData().clear(); // Clear any existing data
        sugarLevelChart.getData().add(sugarSeries);
    }


    public List<HeartRateData> fetchHeartRateData(String userId) {
        List<HeartRateData> readings = new ArrayList<>();
        String query = "SELECT HEART_RATE, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int heartRate = resultSet.getInt("HEART_RATE");
                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();

                readings.add(new HeartRateData(creationDateTime, heartRate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readings;
    }

    public void populateHeartRateChart(List<HeartRateData> data, AreaChart<String, Number> heartRateChart) {
        XYChart.Series<String, Number> heartRateSeries = new XYChart.Series<>();
        heartRateSeries.setName("Heart Rate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (HeartRateData reading : data) {
            if (reading.getHeartRate() == 0) {
//                System.out.println("Skipping data point for date: " + reading.getDateTime() + " because heart rate is 0.");
                continue; // Skip this record
            }

            String formattedDate = reading.getDateTime().format(formatter);
//            System.out.println("Adding data point: " + formattedDate + ", Heart Rate: " + reading.getHeartRate());
            heartRateSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getHeartRate()));
        }

        heartRateChart.getData().clear(); // Clear any existing data
        heartRateChart.getData().add(heartRateSeries);
    }


    public List<OxygenLevelData> fetchOxygenLevelData(String userId) {
        List<OxygenLevelData> readings = new ArrayList<>();
        String query = "SELECT OXYGEN_LEVEL, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int oxygenLevel = resultSet.getInt("OXYGEN_LEVEL");
                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();

                readings.add(new OxygenLevelData(creationDateTime, oxygenLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readings;
    }

    public void populateOxygenChart(List<OxygenLevelData> data, AreaChart<String, Number> oxygenLevelChart) {
        XYChart.Series<String, Number> oxygenLevelSeries = new XYChart.Series<>();
        oxygenLevelSeries.setName("Oxygen Level");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (OxygenLevelData reading : data) {
            if (reading.getOxygenLevel() == 0) {
//                System.out.println("Skipping data point for date: " + reading.getDateTime() + " because oxygen level is 0.");
                continue; // Skip this record
            }

            String formattedDate = reading.getDateTime().format(formatter);
//            System.out.println("Adding data point: " + formattedDate + ", Oxygen Level: " + reading.getOxygenLevel());
            oxygenLevelSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getOxygenLevel()));
        }

        oxygenLevelChart.getData().clear(); // Clear any existing data
        oxygenLevelChart.getData().add(oxygenLevelSeries);
    }


    public List<HealthData> fetchHealthData(String userId) {
        List<HealthData> readings = new ArrayList<>();
        String query = "SELECT BLOOD_PRESSURE, SUGAR_LEVEL, HEART_RATE, OXYGEN_LEVEL, CREATIONDATETIME FROM HEALTHDATA WHERE USER_ID = ?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bloodPressure = resultSet.getString("BLOOD_PRESSURE");
                Integer sugarLevel = resultSet.getInt("SUGAR_LEVEL");
                Integer heartRate = resultSet.getInt("HEART_RATE");
                Integer oxygenLevel = resultSet.getInt("OXYGEN_LEVEL");
                LocalDateTime creationDateTime = resultSet.getTimestamp("CREATIONDATETIME").toLocalDateTime();

                Integer systolic = null;
                Integer diastolic = null;
                if (bloodPressure != null && bloodPressure.contains("/")) {
                    String[] bpParts = bloodPressure.split("/");
                    systolic = Integer.parseInt(bpParts[0]);
                    diastolic = Integer.parseInt(bpParts[1]);
                }

                readings.add(new HealthData(creationDateTime, systolic, diastolic, sugarLevel, heartRate, oxygenLevel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Sort the readings by creationDateTime
        Collections.sort(readings, new Comparator<HealthData>() {
            @Override
            public int compare(HealthData o1, HealthData o2) {
                return o1.getDateTime().compareTo(o2.getDateTime()); // Ascending order
            }
        });

        return readings;
    }

//    public void populateHealthDataChart(List<HealthData> data, AreaChart<String, Number> healthDataChart) {
//        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
//        systolicSeries.setName("Systolic");
//
//        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
//        diastolicSeries.setName("Diastolic");
//
//        XYChart.Series<String, Number> sugarSeries = new XYChart.Series<>();
//        sugarSeries.setName("Sugar Level");
//
//        XYChart.Series<String, Number> heartRateSeries = new XYChart.Series<>();
//        heartRateSeries.setName("Heart Rate");
//
//        XYChart.Series<String, Number> oxygenLevelSeries = new XYChart.Series<>();
//        oxygenLevelSeries.setName("Oxygen Level");
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//        for (HealthData reading : data) {
//            String formattedDate = reading.getDateTime().format(formatter);
//
//            if (reading.getSystolic() != null) {
//                systolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSystolic()));
//            }
//
//            if (reading.getDiastolic() != null) {
//                diastolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getDiastolic()));
//            }
//
//            if (reading.getSugarLevel() != null) {
//                sugarSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSugarLevel()));
//            }
//
//            if (reading.getHeartRate() != null) {
//                heartRateSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getHeartRate()));
//            }
//
//            if (reading.getOxygenLevel() != null) {
//                oxygenLevelSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getOxygenLevel()));
//            }
//        }
//
//        healthDataChart.getData().clear();
//        healthDataChart.getData().addAll(systolicSeries, diastolicSeries, sugarSeries, heartRateSeries, oxygenLevelSeries);
//    }


    public void populateHealthDataChart(List<HealthData> data, AreaChart<String, Number> healthDataChart)  {
        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Systolic Blood Pressure");

        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolic Blood Pressure");

        XYChart.Series<String, Number> sugarSeries = new XYChart.Series<>();
        sugarSeries.setName("Sugar Level");

        XYChart.Series<String, Number> heartRateSeries = new XYChart.Series<>();
        heartRateSeries.setName("Heart Rate");

        XYChart.Series<String, Number> oxygenLevelSeries = new XYChart.Series<>();
        oxygenLevelSeries.setName("Oxygen Level");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (HealthData reading : data) {
            String formattedDate = reading.getDateTime().format(formatter);

            if (reading.getSystolic() != null && reading.getSystolic() > 0) {
                systolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSystolic()));
            }

            if (reading.getDiastolic() != null && reading.getDiastolic() > 0) {
                diastolicSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getDiastolic()));
            }

            if (reading.getSugarLevel() != null && reading.getSugarLevel() > 0) {
                sugarSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getSugarLevel()));
            }

            if (reading.getHeartRate() != null && reading.getHeartRate() > 0) {
                heartRateSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getHeartRate()));
            }

            if (reading.getOxygenLevel() != null && reading.getOxygenLevel() > 0) {
                oxygenLevelSeries.getData().add(new XYChart.Data<>(formattedDate, reading.getOxygenLevel()));
            }
        }

        healthDataChart.getData().clear(); // Clear existing data
        healthDataChart.getData().addAll(systolicSeries, diastolicSeries, sugarSeries, heartRateSeries, oxygenLevelSeries);
    }




}
