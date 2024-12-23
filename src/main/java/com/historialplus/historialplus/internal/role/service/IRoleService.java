package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.internal.role.entites.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Integer id);
}
