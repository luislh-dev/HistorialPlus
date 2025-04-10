package com.historialplus.historialplus.common.constants;

import lombok.Getter;

import java.util.Optional;

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

	public static Optional<RoleEnum> getRoleByName(final String role) {
		for (final RoleEnum roleEnum : RoleEnum.values()) {
			if (roleEnum.name().equals(role)) {
				return Optional.of(roleEnum);
			}
		}
		return Optional.empty();
	}

}
