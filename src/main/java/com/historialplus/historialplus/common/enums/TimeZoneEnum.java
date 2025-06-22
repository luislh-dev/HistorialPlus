package com.historialplus.historialplus.common.enums;

import lombok.Getter;

@Getter
public enum TimeZoneEnum {
	LIMA("America/Lima");

	private final String zoneId;

	TimeZoneEnum(String zoneId) {
		this.zoneId = zoneId;
	}
}
