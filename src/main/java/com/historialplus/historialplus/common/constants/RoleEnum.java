package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum RoleEnum {
	ROLE_ADMIN("Administrador"),
	ROLE_USER("Usuario"),
	ROLE_DOCTOR("Doctor"),
	ROLE_PATIENT("Paciente"),
	ROLE_MANAGEMENT("Gestión");

	private final String displayName;

	RoleEnum(final String displayName) {
		this.displayName = displayName;
	}

}
