package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.model.User;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<User> findAll();

    Optional<User> findById(@NonNull UUID id);

    User save(User user);

    void deleteById(UUID id);
}
