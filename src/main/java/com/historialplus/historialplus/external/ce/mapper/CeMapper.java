package com.historialplus.historialplus.external.ce.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.external.ce.dto.CeExternalResponseDTO;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.CE;

public class CeMapper {
    private CeMapper() {}

    public static MinimalPeopleResponseDto toMinimalPeopleDto(CeResponseDto dto){
        MinimalPeopleResponseDto response = new MinimalPeopleResponseDto();
        response.setName(dto.getName());
        response.setFatherLastName(dto.getMaternalSurname());
        response.setMotherLastName(dto.getPaternalSurname());
        response.setDocumentNumber(dto.getDocumentNumber());
        response.setDocumentType(CE);
        response.setHasExternalSource(Boolean.TRUE);
        response.setDataSource(PersonalDataSourceEnum.RENIEC.getDisplayName());
        return response;
    }

    public static CeResponseDto toCeResponseDto(CeExternalResponseDTO dto){
        return CeResponseDto.builder()
            .name(dto.getName())
            .maternalSurname(dto.getMaternalSurname())
            .paternalSurname(dto.getPaternalSurname())
            .documentNumber(dto.getDocumentNumber())
            .build();
    }

}
