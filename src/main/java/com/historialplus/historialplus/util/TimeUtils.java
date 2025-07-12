package com.historialplus.historialplus.util;

import com.historialplus.historialplus.common.constants.TimeZoneConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtils {
	private TimeUtils() {}

	public static LocalDateTime getCurrentTime() {
 		return ZonedDateTime.now(ZoneId.of(TimeZoneConstants.LIMA)).toLocalDateTime();
	}

	public static Instant getCurrentInstant() {
		return ZonedDateTime.now(ZoneId.of(TimeZoneConstants.LIMA)).toInstant();
	}
}
