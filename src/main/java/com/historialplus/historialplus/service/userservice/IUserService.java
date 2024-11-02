package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.entities.UserEntity;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserEntity> findAll();

    Optional<UserEntity> findById(@NonNull UUID id);

    UserEntity save(UserEntity userEntity);

    void deleteById(UUID id);
}
