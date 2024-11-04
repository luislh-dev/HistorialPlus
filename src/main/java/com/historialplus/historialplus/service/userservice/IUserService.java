package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.UserDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(@NonNull UUID id);

    UserDto save(UserDto userDto);

    void deleteById(UUID id);
}
