package com.historialplus.historialplus.service.roleservice;

import com.historialplus.historialplus.entities.RoleEntity;
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
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
