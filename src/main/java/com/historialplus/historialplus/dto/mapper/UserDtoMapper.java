package com.historialplus.historialplus.dto.mapper;

import com.historialplus.historialplus.dto.UserDto;
import com.historialplus.historialplus.entities.UserEntity;

public class UserDtoMapper {

    // Constructor privado para evitar instanciación
    private UserDtoMapper() {
    }

    // funcion estático para mapear UserEntity a UserDto
    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("El userEntity no puede ser nulo");
        }

        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}