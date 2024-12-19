package com.historialplus.historialplus.dto.userDTOs.request;

import com.historialplus.historialplus.dto.userDTOs.BaseUserCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementCreationDto extends BaseUserCreateDto {

    @NotNull(message = "HospitalId es requerido")
    private Integer hospitalId;

    public ManagementCreationDto(String name, String email, String password, int state, String dni, Integer hospitalId) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setStateId(state);
        this.setPersonDNI(dni);
        this.hospitalId = hospitalId;
    }
}