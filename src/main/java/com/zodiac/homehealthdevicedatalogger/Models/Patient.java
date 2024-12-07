package com.zodiac.homehealthdevicedatalogger.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {


    private LocalDate date;
    private String bloodPressure;
    private String sugarLevel;
    private int heartRate;
    private int oxygenLevel;
    private String comments;
    private String userId;
    private LocalDateTime creationDateTime;

    // Constructor
    public Patient(LocalDate date, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comments, LocalDateTime creationDateTime) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comments = comments;
        this.creationDateTime = creationDateTime;
    }

    public Patient(LocalDate date, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
    }



    // Getters and Setters

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
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

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
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
