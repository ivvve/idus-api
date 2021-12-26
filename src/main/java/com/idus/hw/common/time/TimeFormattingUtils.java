package com.idus.hw.common.time;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeFormattingUtils {
    public static String toZonedTimeStampFormat(Instant time, ZoneId zoneId) {
        return time.atZone(zoneId).toString();
    }
}
