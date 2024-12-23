package com.historialplus.historialplus.hospital.dto.request;

import com.historialplus.historialplus.common.validators.hospital.UniqueHospitalField;
import com.historialplus.historialplus.common.validators.state.ValidStateId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalCreateDto {

    @NotNull(message = "Nombre es requerido")
    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    @UniqueHospitalField(fieldName = "name", message = "El nombre ya está en uso")
    private String name;

    @NotNull(message = "Dirección es requerida")
    @Size(min = 3, max = 100, message = "Dirección debe tener entre 3 y 100 caracteres")
    private String address;

    @NotNull(message = "Teléfono es requerido")
    @Pattern(regexp = "^9[0-9]{8}$", message = "Teléfono debe empezar con 9 y tener 9 dígitos")
    @UniqueHospitalField(fieldName = "phone", message = "El teléfono ya está en uso")
    private String phone;

    @NotNull(message = "Email es requerido")
    @Email(message = "Email no es válido")
    @UniqueHospitalField(fieldName = "email", message = "El email ya está en uso")
    private String email;

    @NotNull(message = "RUC es requerido")
    @Pattern(regexp = "^[0-9]{11}$", message = "RUC debe tener 11 dígitos")
    @UniqueHospitalField(fieldName = "ruc", message = "El RUC ya está en uso")
    private String ruc;

    @NotNull(message = "Estado es requerido")
    @ValidStateId
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