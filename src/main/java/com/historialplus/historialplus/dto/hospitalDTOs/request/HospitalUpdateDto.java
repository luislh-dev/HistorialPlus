package com.historialplus.historialplus.dto.hospitalDTOs.request;

import com.historialplus.historialplus.validators.state.ValidStateId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalUpdateDto {

    @Min(value = 3, message = "Nombre debe tener al menos 3 caracteres")
    private String name;

    private String address;

    @Pattern(regexp = "^[0-9]{9}$", message = "Teléfono debe tener 9 dígitos")
    private String phone;

    @Email(message = "Email no es válido")
    private String email;

    @Pattern(regexp = "^[0-9]{11}$", message = "RUC debe tener 11 dígitos")
    private String ruc;

    @ValidStateId
    private Integer stateId;
}
