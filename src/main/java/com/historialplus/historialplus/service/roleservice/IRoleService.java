package com.historialplus.historialplus.service.roleservice;

import com.historialplus.historialplus.model.RoleModel;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<RoleModel> findAll();
   Optional<RoleModel> findById(Integer id);
}
