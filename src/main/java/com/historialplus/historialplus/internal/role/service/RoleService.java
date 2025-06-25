package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.common.constants.RoleEnum;
import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDto> findAll();
    Optional<RoleDto> findById(Integer id);
    Optional<RoleEntity> findByName(RoleEnum name);
}
