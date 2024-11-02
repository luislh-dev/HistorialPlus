package com.historialplus.historialplus.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private String name;
    private String email;

    public UserDto() {
    }

    public UserDto(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
