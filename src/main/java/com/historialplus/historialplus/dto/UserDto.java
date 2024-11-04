package com.historialplus.historialplus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    @NotBlank(message = "Nombre es requerido")
    private String name;
    private String email;
    private int stateId;

    public UserDto(UUID id, String name, String email, int stateId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.stateId = stateId;
    }

}
