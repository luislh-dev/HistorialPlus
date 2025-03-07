package com.historialplus.historialplus.internal.user.mapper;

import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDtoMapper() {}

    // función estática para mapear UserEntity a UserResponseDto
    public static UserResponseDto toResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserResponseDto(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getState().getId(), userEntity.getRoleEntities().stream().map(RoleEntity::getId).toList());
    }
}