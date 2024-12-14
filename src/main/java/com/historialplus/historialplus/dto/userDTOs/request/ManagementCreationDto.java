package com.historialplus.historialplus.dto.userDTOs.request;

import com.historialplus.historialplus.dto.userDTOs.BaseUserCreateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ManagementCreationDto extends BaseUserCreateDto {

    @NotBlank(message = "El id del hospital no puede estar vacío")
    private final Integer hospitalId;

    public ManagementCreationDto(String name, String email, String password, int state, String dni, Integer hospitalId) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setStateId(state);
        this.setPersonDNI(dni);
        this.hospitalId = hospitalId;
    }
}