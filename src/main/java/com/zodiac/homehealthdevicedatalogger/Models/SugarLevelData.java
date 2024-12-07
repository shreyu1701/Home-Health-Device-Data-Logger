package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDateTime;

public class SugarLevelData {
    private final LocalDateTime dateTime;
    private final int sugarLevel;

    public SugarLevelData(LocalDateTime dateTime, int sugarLevel) {
        this.dateTime = dateTime;
        this.sugarLevel = sugarLevel;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getSugarLevel() {
        return sugarLevel;
    }
}
