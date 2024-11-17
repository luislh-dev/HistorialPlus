package com.historialplus.historialplus.dto.userDTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListResponseDto {
    private final String username;
    private final String email;
    private final String dni;
    private final String hospital;
    private final String state;
    private final String roles;

    public UserListResponseDto(String username, String email, String dni, String hospital, String state, String roles) {
        this.username = username;
        this.email = email;
        this.dni = dni;
        this.hospital = hospital;
        this.state = state;
        this.roles = roles;
    }

}
