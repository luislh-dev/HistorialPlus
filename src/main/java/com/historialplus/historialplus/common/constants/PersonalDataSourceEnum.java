package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum PersonalDataSourceEnum {
	RENIEC("Reniec"),
	INTERNAL("Sistema Interno");

	private final String displayName;

	PersonalDataSourceEnum(String displayName) {
		this.displayName = displayName;
	}

}
