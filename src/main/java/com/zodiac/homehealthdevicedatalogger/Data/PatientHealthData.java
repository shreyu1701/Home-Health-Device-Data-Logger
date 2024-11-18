package com.zodiac.homehealthdevicedatalogger.Data;

import java.time.LocalDate;

public class PatientHealthData {
    private LocalDate date;
    private String bloodPressure;
    private String sugarLevel;
    private String heartRate;
    private String oxygenLevel;
    private String comment;

    // Default constructor (no-argument constructor)
    public PatientHealthData() {
    }
    // Constructor
    public PatientHealthData(LocalDate date, String bloodPressure, String sugarLevel, String heartRate, String oxygenLevel, String comment) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comment;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    // No need for a String setter for date, Jackson will map it automatically
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(String oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "PatientHealthData{" +
                "date=" + date +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", sugarLevel='" + sugarLevel + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", oxygenLevel='" + oxygenLevel + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
