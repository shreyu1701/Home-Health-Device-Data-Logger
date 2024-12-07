package com.zodiac.homehealthdevicedatalogger.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientHealthData {
    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private String gender;
    private String email;
    private String bloodGroup;


    private LocalDate date;
    //private Date dataDate;
    private String bloodPressure;
    private String sugarLevel;
    private int heartRate;
    private int oxygenLevel;
    private String comment;
    private String userId;
    private LocalDateTime creationDateTime;
    private String historyDate;



    public PatientHealthData(LocalDate date, String userId, String bloodPressure, String sugarLevel, String heartRate, String oxygenLevel) {
        this.date = date;
        this.userId = userId;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        if (heartRate != null && !heartRate.trim().isEmpty()) {
            this.heartRate = Integer.parseInt(heartRate);
        } else {
            this.heartRate = 0; // Default value or handle as needed
        }

        if (oxygenLevel != null && !oxygenLevel.trim().isEmpty()) {
            this.oxygenLevel = Integer.parseInt(oxygenLevel);
        } else {
            this.oxygenLevel = 0; // Default value or handle as needed
        }

    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PatientHealthData(String firstName, String lastName, int age, String phone, String gender, String email, String bloodGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.bloodGroup = bloodGroup;
    }
    // Default constructor (no-argument constructor)
    public PatientHealthData(String userId, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comments) {

        this.userId = userId;
        //this.dataDate = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comments;

    }
    // Constructor
    public PatientHealthData(LocalDate date, String bloodPressure, String sugarLevel, Integer heartRate, Integer oxygenLevel, String comment, LocalDateTime creationDateTime) {
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comment;
        this.creationDateTime = creationDateTime;
    }


    // Constructor
    public PatientHealthData( String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comment, LocalDateTime creationDateTime) {
       // this.dataDate = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comment;
        this.creationDateTime = creationDateTime;
    }

    public PatientHealthData(String userId, LocalDate dataDate, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel, String comments, LocalDateTime creationDateTime) {
        this.userId = userId;
        this.date = dataDate;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.comment = comments;
        this.creationDateTime = creationDateTime;

    }

    public PatientHealthData(LocalDate date, String bloodPressure, String sugarLevel, int heartRate, int oxygenLevel) {

        this.date = date;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;

    }

    // Getters and Setters
//    public Date getDataDate(){
//        return dataDate;
//    }
//
//    public void setDataDate(Date dataDate){
//        this.dataDate = dataDate;
//    }
    public LocalDate getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodGroup() {
        return bloodGroup;
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
