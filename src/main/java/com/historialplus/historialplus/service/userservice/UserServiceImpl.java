package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.UserDto;
import com.historialplus.historialplus.dto.mapper.UserDtoMapper;
import com.historialplus.historialplus.entities.RoleEntity;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
import com.historialplus.historialplus.service.roleservice.IRoleService;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(@NonNull UUID id) {
        return repository.findById(id)
                .map(UserDtoMapper::toDto);
    }

    @Override
    @Transactional
    public UserDto save(UserEntity userEntity) {

        if (userEntity.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }

        // colocar por defecto el rol 2 (ROLE_USER)
        RoleEntity roleEntity = roleService.findById(2).orElseThrow();
        userEntity.setRoleEntities(List.of(roleEntity));

        UserEntity savedEntity = repository.save(userEntity);
        return UserDtoMapper.toDto(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}