package com.historialplus.historialplus.dto.mapper;

import com.historialplus.historialplus.dto.UserDto;
import com.historialplus.historialplus.entities.StateEntity;
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

        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getStateEntity().getId());
    }

    // funcion estático para mapear UserDto a UserEntity
    public static UserEntity toEntity(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("El userDto no puede ser nulo");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());

        // colocar el stataId en el estado del usuario
        StateEntity stateEntity = new StateEntity();
        stateEntity.setId(userDto.getStateId());

        userEntity.setStateEntity(stateEntity);
        return userEntity;
    }
}