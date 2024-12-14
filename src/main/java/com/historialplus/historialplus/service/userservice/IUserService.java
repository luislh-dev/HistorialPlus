package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.userDTOs.request.DoctorCreationDto;
import com.historialplus.historialplus.dto.userDTOs.request.ManagementCreationDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserUpdateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserListResponseDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.UserEntity;
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
