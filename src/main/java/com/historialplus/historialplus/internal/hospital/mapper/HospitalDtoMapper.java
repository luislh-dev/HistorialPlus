package com.historialplus.historialplus.internal.hospital.mapper;

import com.historialplus.historialplus.internal.hospital.dto.request.HospitalCreateDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;

public class HospitalDtoMapper {

    private HospitalDtoMapper() {
    }

    public static HospitalResponseDto toHospitalResponseDto(HospitalEntity hospitalEntity) {
        return new HospitalResponseDto(
                hospitalEntity.getId(),
                hospitalEntity.getName(),
                hospitalEntity.getPhone(),
                hospitalEntity.getEmail(),
                hospitalEntity.getRuc(),
                hospitalEntity.getState().getName()
        );
    }

    public static HospitalEntity toHospitalEntity(HospitalCreateDto hospitalDto) {
        HospitalEntity hospital = new HospitalEntity();
        hospital.setAddress(hospitalDto.getAddress());
        hospital.setName(hospitalDto.getName());
        hospital.setPhone(hospitalDto.getPhone());
        hospital.setEmail(hospitalDto.getEmail());
        hospital.setRuc(hospitalDto.getRuc());
        return hospital;
    }

    public static HospitalFindByResponseDto toUserFindByResponseDto(HospitalEntity hospitalEntity) {
        return new HospitalFindByResponseDto(
                hospitalEntity.getName(),
                hospitalEntity.getAddress(),
                hospitalEntity.getPhone(),
                hospitalEntity.getEmail(),
                hospitalEntity.getRuc(),
                hospitalEntity.getState().getId()
        );
    }
}
