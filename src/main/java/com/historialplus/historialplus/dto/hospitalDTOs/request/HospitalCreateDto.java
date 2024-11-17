package com.historialplus.historialplus.dto.hospitalDTOs.request;

import com.historialplus.historialplus.validators.state.ValidStateId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalCreateDto {

    @NotNull(message = "Nombre es requerido")
    @Min(value = 3, message = "Nombre debe tener al menos 3 caracteres")
    private String name;

    @NotNull(message = "Dirección es requerida")
    private String address;

    @NotNull(message = "Teléfono es requerido")
    @Pattern(regexp = "^[0-9]{9}$", message = "Teléfono debe tener 9 dígitos")
    private String phone;

    @NotNull(message = "Email es requerido")
    @Email(message = "Email no es válido")
    private String email;

    @NotNull(message = "RUC es requerido")
    @Pattern(regexp = "^[0-9]{11}$", message = "RUC debe tener 11 dígitos")
    private String ruc;

    @ValidStateId
    @NotNull(message = "Estado es requerido")
    private Integer stateId;

    public HospitalCreateDto(String name, String address, String phone, String email, String ruc, Integer stateId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
        this.stateId = stateId;
    }
}