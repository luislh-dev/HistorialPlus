package com.historialplus.historialplus.dto.userDTOs.mapper;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.entities.UserEntity;

public class UserDtoMapper {

    // Constructor privado para evitar instanciación
    private UserDtoMapper() {
    }

    // función estática para mapear UserEntity a UserDto
    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getStateEntity().getId());
    }

    // función estática para mapear UserCreateDto a UserEntity
    public static UserEntity toEntity(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new IllegalArgumentException("El userCreateDto no puede ser nulo");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userCreateDto.getName());
        userEntity.setEmail(userCreateDto.getEmail());
        userEntity.setPassword(userCreateDto.getPassword());

        // colocar el stateId en el estado del usuario
        StateEntity stateEntity = new StateEntity();
        stateEntity.setId(userCreateDto.getStateId());

        userEntity.setStateEntity(stateEntity);
        return userEntity;
    }

    // función estática para mapear UserEntity a UserResponseDto
    public static UserResponseDto toResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserResponseDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getStateEntity().getId());
    }
}