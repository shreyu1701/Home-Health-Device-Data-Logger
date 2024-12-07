package com.zodiac.homehealthdevicedatalogger.Models;

import java.time.LocalDateTime;

public class OxygenLevelData {
    private final LocalDateTime dateTime;
    private final int oxygenLevel;

    public OxygenLevelData(LocalDateTime dateTime, int oxygenLevel) {
        this.dateTime = dateTime;
        this.oxygenLevel = oxygenLevel;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }
}
