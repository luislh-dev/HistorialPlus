package com.historialplus.historialplus.dto.hospitalDTOs.request;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalCreateDto {
    private String name;
    private String phone;
    private String email;
    private String ruc;
    private Integer stateId;
    private UserCreateDto adminUser; // Datos del usuario administrador
    private PeopleCreateDto adminPerson; // Datos de la persona asociada al administrador
}