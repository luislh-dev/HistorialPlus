package com.historialplus.historialplus.internal.role.mapper;

import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDtoMapper {
    public static RoleDto toRoleDto(RoleEntity roleEntity) {
        return new RoleDto(roleEntity.getId(), roleEntity.getName());
    }

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleDto.getId());
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }

    public static List<RoleEntity> toRoleEntityList(List<RoleDto> roleDtos) {
        return roleDtos.stream().map(RoleDtoMapper::toRoleEntity).collect(Collectors.toList());
    }
}
