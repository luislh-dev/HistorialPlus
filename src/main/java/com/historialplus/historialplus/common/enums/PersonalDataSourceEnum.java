package com.historialplus.historialplus.common.enums;

import lombok.Getter;

@Getter
public enum PersonalDataSourceEnum {
	RENIEC("Reniec"),
	MIGRATION("Migración"),
	INTERNAL("Sistema Interno");

	private final String displayName;

	PersonalDataSourceEnum(String displayName) {
		this.displayName = displayName;
	}

}
