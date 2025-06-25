package com.historialplus.historialplus.internal.user.service;

import com.historialplus.historialplus.internal.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void deleteById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto userDto);

    Page<UserListResponseDto> searchUsers(String name, String dni, String hospitalName, Integer roleId, Integer stateId, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

    UserResponseDto createManagementUser(ManagementCreationDto userDto);

    UserResponseDto createDoctorUser(DoctorCreationDto userDto);
}
