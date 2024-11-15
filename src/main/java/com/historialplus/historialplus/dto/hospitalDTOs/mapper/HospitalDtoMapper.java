package com.historialplus.historialplus.dto.hospitalDTOs.mapper;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalListDto;
import com.historialplus.historialplus.entities.HospitalEntity;

public class HospitalDtoMapper {

    private HospitalDtoMapper() {
    }

    public static HospitalListDto toHospitalListDto(HospitalEntity hospitalEntity) {
        return new HospitalListDto(
                hospitalEntity.getId(),
                hospitalEntity.getName(),
                hospitalEntity.getPhone(),
                hospitalEntity.getEmail(),
                hospitalEntity.getRuc(),
                hospitalEntity.getState().getName()
        );
    }
}
