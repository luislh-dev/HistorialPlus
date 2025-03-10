package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.internal.role.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<RoleDto> findAll();
    Optional<RoleDto> findById(Integer id);
}
