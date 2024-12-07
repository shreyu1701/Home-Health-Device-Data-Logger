package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDateTime;

public class HeartRateData{
private final LocalDateTime dateTime;
private final int heartRate;

public HeartRateData(LocalDateTime dateTime, int heartRate) {
    this.dateTime = dateTime;
    this.heartRate = heartRate;
}

public LocalDateTime getDateTime() {
    return dateTime;
}

public int getHeartRate() {
    return heartRate;
}
    }
