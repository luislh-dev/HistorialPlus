package com.historialplus.historialplus.role.service;

import com.historialplus.historialplus.role.entites.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Integer id);
    Optional<RoleEntity> finByName(String name);
}
