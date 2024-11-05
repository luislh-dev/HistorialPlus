package com.historialplus.historialplus.dto.userDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID id;

    @NotBlank(message = "Nombre es requerido")
    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @Email(message = "Email no es válido")
    private String email;

    // no debe ser nulo o 0

    private int stateId;

    public UserDto(UUID id, String name, String email, int stateId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.stateId = stateId;
    }

}
