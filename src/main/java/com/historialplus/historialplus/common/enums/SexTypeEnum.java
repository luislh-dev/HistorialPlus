package com.historialplus.historialplus.common.enums;

import lombok.Getter;

@Getter
public enum SexTypeEnum {
	MALE("Masculino"),
	FEMALE("Femenino");

	private final String displayName;

	SexTypeEnum(String displayName) {
		this.displayName = displayName;
	}

}
