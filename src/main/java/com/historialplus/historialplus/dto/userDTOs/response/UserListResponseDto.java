package com.historialplus.historialplus.dto.userDTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserListResponseDto {
    private final UUID id;
    private final String username;
    private final String email;
    private final String dni;
    private final String hospital;
    private final String state;
    private final List<String> roles;

    public UserListResponseDto(UUID id, String username, String email, String dni, String hospital, String state, List<String>  roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dni = dni;
        this.hospital = hospital;
        this.state = state;
        this.roles = roles;
    }

}
