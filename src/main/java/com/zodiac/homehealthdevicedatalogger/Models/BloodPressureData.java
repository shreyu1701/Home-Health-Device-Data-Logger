package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDateTime;

public class BloodPressureData {
    private final LocalDateTime dateTime;
    private final int systolic;
    private final int diastolic;

    public BloodPressureData(LocalDateTime dateTime, int systolic, int diastolic) {
        this.dateTime = dateTime;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getSystolic() {
        return systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }
}
