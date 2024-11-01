package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.model.UserModel;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserModel> findAll();

    Optional<UserModel> findById(@NonNull UUID id);

    UserModel save(UserModel userModel);

    void deleteById(UUID id);
}
