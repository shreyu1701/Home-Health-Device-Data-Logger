package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HealthData {
    private final LocalDateTime dateTime;
    private final Integer systolic;
    private final Integer diastolic;
    private final Integer sugarLevel;
    private final Integer heartRate;
    private final Integer oxygenLevel;

    public HealthData(LocalDateTime dateTime, Integer systolic, Integer diastolic, Integer sugarLevel, Integer heartRate, Integer oxygenLevel) {
        this.dateTime = dateTime;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public String getBloodPressure() { return String.valueOf(getSystolic() + getDiastolic());}

    public Integer getSugarLevel() {
        return sugarLevel;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public Integer getOxygenLevel() {
        return oxygenLevel;
    }

}
