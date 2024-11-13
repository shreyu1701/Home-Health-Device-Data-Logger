package com.zodiac.homehealthdevicedatalogger.Data;

import java.time.LocalDate;

public class PatientHealthData {

    private LocalDate date;
    private String bloodPressure;
    private int sugarLevel;
    private int heartRate;
    private int oxygenLevel;
    private String comments;

    public PatientHealthData(LocalDate date, String bloodPressure, int sugarLevel, int heartRate, int oxygenLevel, String comments) {
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

    public int getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(int sugarLevel) {
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

    @Override
    public String toString() {
        return "PatientHealthData{" +
                "date=" + date +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", sugarLevel=" + sugarLevel +
                ", heartRate=" + heartRate +
                ", oxygenLevel=" + oxygenLevel +
                ", comments='" + comments + '\'' +
                '}';
    }
}
