package com.historialplus.historialplus.service.roleservice;

import com.historialplus.historialplus.entities.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Integer id);
    Optional<RoleEntity> finByName(String name);
}
