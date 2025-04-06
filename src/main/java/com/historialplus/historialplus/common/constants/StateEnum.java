package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum StateEnum {
	ACTIVE("Activo"),
	INACTIVE("Inactivo"),
	DELETED("Eliminado");

	private final String displayName;

	StateEnum(String displayName) {
		this.displayName = displayName;
	}
}
