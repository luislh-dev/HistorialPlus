package com.historialplus.historialplus.dto.userDTOs.request;

import com.historialplus.historialplus.validators.role.ValidRoleId;
import com.historialplus.historialplus.validators.state.ValidStateId;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserCreateDto {

    @NotBlank(message = "Nombre es requerido")
    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @NotBlank(message = "Email es requerido")
    @Email(message = "Email no es válido")
    private String email;

    @NotBlank(message = "Contraseña es requerida")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$",
            message = "Contraseña debe tener al menos 8 caracteres, una letra mayúscula, un número y un caracter especial"
    )
    private String password;

    @NotNull(message = "Estado es requerido")
    @ValidStateId
    private Integer stateId;

    @NotNull(message = "Roles son requeridos")
    @ValidRoleId
    private Integer roleId;

    public UserCreateDto(String name, String email, String password, int state, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.stateId = state;
        this.roleId = roleId;
    }
}