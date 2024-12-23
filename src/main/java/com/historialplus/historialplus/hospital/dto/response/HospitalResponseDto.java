package com.historialplus.historialplus.hospital.dto.response;

import lombok.Getter;

@Getter
public class HospitalResponseDto {
    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    private final String ruc;
    private final String state;

    public HospitalResponseDto(int id, String name, String phone, String email, String ruc, String state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
        this.state = state;
    }
}
