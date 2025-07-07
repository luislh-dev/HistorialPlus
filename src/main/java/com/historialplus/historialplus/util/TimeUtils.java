package com.historialplus.historialplus.util;

import com.historialplus.historialplus.common.constants.TimeZoneConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {
	private TimeUtils() {}

	public static LocalDateTime getCurrentTime() {
 		return LocalDateTime.now().atZone(ZoneId.of(TimeZoneConstants.LIMA)).toLocalDateTime();
	}

	public static Instant getCurrentInstant() {
		return Instant.now().atZone(ZoneId.of(TimeZoneConstants.LIMA)).toInstant();
	}
}
