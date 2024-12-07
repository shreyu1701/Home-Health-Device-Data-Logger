package com.zodiac.homehealthdevicedatalogger.Models;

public class HealthRecord {

    private final String date;
    private final String userId;
    private final String bloodPressure;
    private final String sugarLevel;
    private final String heartRate;
    private final String oxygenLevel;

    public HealthRecord(String date, String userId, String bloodPressure, String sugarLevel, String heartRate, String oxygenLevel) {
        this.date = date;
        this.userId = userId;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getOxygenLevel() {
        return oxygenLevel;
    }
}

