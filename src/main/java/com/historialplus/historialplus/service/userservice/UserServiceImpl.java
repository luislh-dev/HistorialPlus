package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.entities.RoleEntity;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
import com.historialplus.historialplus.service.roleservice.IRoleService;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, IRoleService roleService) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {

        if (userEntity.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }

        // colocar por defecto el rol 2 (ROLE_USER)
        RoleEntity roleEntity = roleService.findById(2).orElseThrow();
        userEntity.setRoleEntities(List.of(roleEntity));

        return repository.save(userEntity);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}