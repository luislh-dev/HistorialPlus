package com.historialplus.historialplus.internal.hospital.dto.response;

public record HospitalResponseDto(
        Integer id,
        String name,
        String phone,
        String email,
        String ruc,
        String stateName
) {}
