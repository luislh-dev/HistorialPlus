package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.common.constants.RoleEnum;
import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.role.mapper.RoleMapper;
import com.historialplus.historialplus.internal.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        List<RoleEntity> roles = roleRepository.findAll();

        return roles.stream()
            .map(RoleMapper::roleEntityToRoleDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findById(Integer id) {
        return roleRepository.findById(id).map(RoleMapper::roleEntityToRoleDto);
    }

    @Override public Optional<RoleEntity> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
