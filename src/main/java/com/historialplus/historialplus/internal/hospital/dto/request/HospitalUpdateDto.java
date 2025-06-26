package com.historialplus.historialplus.internal.hospital.dto.request;

import com.historialplus.historialplus.common.validators.state.ValidStateId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalUpdateDto {

    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    private String name;

    private String address;

    @Pattern(regexp = "^\\d{9}$", message = "Teléfono debe tener 9 dígitos")
    private String phone;

    @Email(message = "Email no es válido")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "RUC debe tener 11 dígitos")
    private String ruc;

    @ValidStateId
    private Integer stateId;
}
