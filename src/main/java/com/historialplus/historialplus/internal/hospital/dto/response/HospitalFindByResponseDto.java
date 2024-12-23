package com.historialplus.historialplus.internal.hospital.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalFindByResponseDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String ruc;
    private Integer stateId;

    public HospitalFindByResponseDto(String name, String address, String phone, String email, String ruc, Integer stateId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
        this.stateId = stateId;
    }
}
