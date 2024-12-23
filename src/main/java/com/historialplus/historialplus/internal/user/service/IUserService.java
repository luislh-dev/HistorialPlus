package com.historialplus.historialplus.internal.user.service;

import com.historialplus.historialplus.internal.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserResponseDto> findAll();

    Optional<UserResponseDto> findById(@NonNull UUID id);

    void deleteById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto userDto);

    Page<UserListResponseDto> searchUsers(String name, String dni, String hospitalName, Integer roleId, Integer stateId, Pageable pageable);

    UserEntity findByUsername(String username);

    UserResponseDto createManagementUser(ManagementCreationDto userDto);

    UserResponseDto createDoctorUser(DoctorCreationDto userDto);
}
