package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.auth.AuthService.IAuthService;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.common.constants.RoleConstants.ROLE_ADMIN;
import static com.historialplus.historialplus.util.roles.RoleTransformer.transformRoles;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IAuthService authService;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, IAuthService authService) {
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @Override
    public List<RoleEntity> findAll() {
        String role = authService.getAuthenticatedUserRole();

        return authService.isAdmin(role)
                ? transformRoles(roleRepository.findAll())
                : transformRoles(getNonAdminRoles());
    }

    @Override
    public Optional<RoleEntity> findById(Integer id) {
        return roleRepository.findById(id);
    }

    private List<RoleEntity> getNonAdminRoles() {
        return roleRepository.findAll().stream()
                .filter(roleEntity -> !ROLE_ADMIN.equals(roleEntity.getName()))
                .collect(Collectors.toList());
    }

}
