package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDate;

public class Patient {
    private LocalDate date;
    private String bloodPressure;
    private String sugarLevel;
    private String heartRate;
    private String oxygenLevel;
    private String comments;

    // Constructor
    public Patient(LocalDate date, String bloodPressure, String sugarLevel, String heartRate, String oxygenLevel, String comments) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comments = comments;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // Optional: Override toString() for easy data display
    @Override
    public String toString() {
        return "Patient{" +
                "date=" + date +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", sugarLevel='" + sugarLevel + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", oxygenLevel='" + oxygenLevel + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
