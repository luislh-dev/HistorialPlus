package com.historialplus.historialplus.internal.role.mapper;

import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;

public class RoleMapper {
	private RoleMapper() {}

	public static RoleDto roleEntityToRoleDto(RoleEntity roleEntity) {
		return RoleDto.builder()
				.id(roleEntity.getId())
				.name(roleEntity.getName().getDisplayName())
				.build();
	}

}
