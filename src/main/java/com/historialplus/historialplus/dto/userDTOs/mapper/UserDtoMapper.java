package com.historialplus.historialplus.dto.userDTOs.mapper;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserListResponseDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.RoleEntity;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // función estática para mapear UserEntity a UserDto
    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getStateEntity().getId());
    }

    public static UserListResponseDto toListResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserListResponseDto(
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPerson() != null && userEntity.getPerson().getDocumentNumber() != null ? userEntity.getPerson().getDocumentNumber() : "",
                userEntity.getHospital() != null && userEntity.getHospital().getName() != null ? userEntity.getHospital().getName() : "",
                userEntity.getStateEntity() != null && userEntity.getStateEntity().getName() != null ? userEntity.getStateEntity().getName() : "",
                !userEntity.getRoleEntities().isEmpty() && userEntity.getRoleEntities().getFirst().getName() != null ? userEntity.getRoleEntities().getFirst().getName() : ""
        );
    }

    // función estática para mapear UserCreateDto a UserEntity
    public UserEntity toEntity(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new IllegalArgumentException("El userCreateDto no puede ser nulo");
        }
        var userEntity = new UserEntity();
        userEntity.setName(userCreateDto.getName());
        userEntity.setEmail(userCreateDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword())); // Encriptar la contraseña

        // Crear un objeto StateEntity con el id del estado
        StateEntity stateEntity = new StateEntity();
        stateEntity.setId(userCreateDto.getStateId());
        userEntity.setStateEntity(stateEntity);

        // Crear un objeto RoleEntity con el id del rol
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(userCreateDto.getRoleId());
        userEntity.setRoleEntities(List.of(roleEntity));

        return userEntity;
    }

    // función estática para mapear UserEntity a UserResponseDto
    public static UserResponseDto toResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserResponseDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getStateEntity().getId(), userEntity.getRoleEntities().stream().map(RoleEntity::getId).toList());
    }
}