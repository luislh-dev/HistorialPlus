package com.historialplus.historialplus.service.roleservice;

import com.historialplus.historialplus.model.RoleModel;
import com.historialplus.historialplus.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl  implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleModel> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleModel> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
