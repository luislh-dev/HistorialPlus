package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.UserDto;
import com.historialplus.historialplus.entities.UserEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(@NonNull UUID id);

    UserDto save(UserEntity userEntity);

    void updateLastLoginAt(String username);

    void deleteById(UUID id);
}
