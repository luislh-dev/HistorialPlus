package com.historialplus.historialplus.common.constants;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum RoleEnum {
	ROLE_ADMIN("Administrador", "ADMIN"),
	ROLE_USER("Usuario", "USER"),
	ROLE_DOCTOR("Doctor", "DOCTOR"),
	ROLE_PATIENT("Paciente", "PATIENT"),
	ROLE_MANAGEMENT("Gestión", "MANAGEMENT");

	private final String displayName;
	private final String roleName;

	RoleEnum(final String displayName, final String roleName) {
		this.displayName = displayName;
		this.roleName = roleName;
	}

	public static String ADMIN() {
		return ROLE_ADMIN.getRoleName();
	}

	public static String USER() {
		return ROLE_USER.getRoleName();
	}

	public static String MANAGEMENT() {
		return ROLE_MANAGEMENT.getRoleName();
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
