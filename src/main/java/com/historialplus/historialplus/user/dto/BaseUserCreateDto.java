package com.historialplus.historialplus.user.dto;

import com.historialplus.historialplus.common.validators.state.ValidStateId;
import com.historialplus.historialplus.common.validators.user.UniqueUserField;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import static com.historialplus.historialplus.common.validators.user.UserFieldName.EMAIL;
import static com.historialplus.historialplus.common.validators.user.UserFieldName.USERNAME;

@Getter
@Setter
public class BaseUserCreateDto {
    @NotBlank(message = "Nombre es requerido")
    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    @UniqueUserField(fieldName = USERNAME, message = "Nombre de usuario ya existe")
    private String name;

    @NotBlank(message = "Email es requerido")
    @Email(message = "Email no es válido")
    @UniqueUserField(fieldName = EMAIL, message = "Email ya existe")
    private String email;

    @NotBlank(message = "Contraseña es requerida")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$",
            message = "Contraseña debe tener al menos 8 caracteres, una letra mayúscula, un número y un caracter especial"
    )
    private String password;

    @NotBlank(message = "DNI es requerido")
    private String personDNI;

    @NotNull(message = "Estado es requerido")
    @ValidStateId
    private Integer stateId;
}
