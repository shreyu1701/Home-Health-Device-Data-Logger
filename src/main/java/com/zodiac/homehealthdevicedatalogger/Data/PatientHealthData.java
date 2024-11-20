package com.zodiac.homehealthdevicedatalogger.Data;

import java.sql.Date;
import java.time.LocalDate;

public class PatientHealthData {
    private LocalDate date;
    private Date dataDate;
    private String bloodPressure;
    private String sugarLevel;
    private int heartRate;
    private int oxygenLevel;
    private String comment;
    private String userId;

    // Default constructor (no-argument constructor)
    public PatientHealthData(String userId, Date dataDate, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comments) {

        this.userId = userId;
        this.dataDate = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comments;

    }
    // Constructor
    public PatientHealthData(LocalDate date, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comment) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comment;
    }

    // Constructor
    public PatientHealthData(Date dataDate, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comment) {
        this.dataDate = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comment;
    }

    public PatientHealthData(String userId, LocalDate dataDate, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comments) {
        this.userId = userId;
        this.date = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comments;

    }

    public PatientHealthData(LocalDate date, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel) {

        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;

    }

    // Getters and Setters
    public Date getDataDate(){
        return dataDate;
    }

    public void setDataDate(Date dataDate){
        this.dataDate = dataDate;
    }
    public LocalDate getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
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
