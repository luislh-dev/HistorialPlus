package com.historialplus.historialplus.user.mapper;

import com.historialplus.historialplus.role.entites.RoleEntity;
import com.historialplus.historialplus.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.user.entites.UserEntity;
import org.springframework.stereotype.Component;

import static com.historialplus.historialplus.util.roles.RoleTransformer.transformRoles;

@Component
public class UserDtoMapper {

    public UserDtoMapper() {}

    public static UserListResponseDto toListResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserListResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPerson() != null && userEntity.getPerson().getDocumentNumber() != null ? userEntity.getPerson().getDocumentNumber() : "",
                userEntity.getHospital() != null && userEntity.getHospital().getName() != null ? userEntity.getHospital().getName() : "",
                userEntity.getState() != null && userEntity.getState().getName() != null ? userEntity.getState().getName() : "",
                transformRoles(userEntity.getRoleEntities()).stream().map(RoleEntity::getName).toList()
        );
    }


    // función estática para mapear UserEntity a UserResponseDto
    public static UserResponseDto toResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserResponseDto(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getState().getId(), userEntity.getRoleEntities().stream().map(RoleEntity::getId).toList());
    }
}