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

    public HospitalCreateDto(String name, String address, String phone, String email, String ruc, Integer stateId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
        this.stateId = stateId;
    }
}