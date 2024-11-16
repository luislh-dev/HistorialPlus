package com.historialplus.historialplus.dto.hospitalDTOs.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalCreateDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String ruc;
    private Integer stateId;
}