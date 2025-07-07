package com.historialplus.historialplus.external.ce.mapper;

import com.historialplus.historialplus.common.enums.PersonalDataSourceEnum;
import com.historialplus.historialplus.external.ce.dto.CeExternalResponseDTO;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.enums.DocumentTypeEnum.CE;

public class CeMapper {
    private CeMapper() {}

    public static MinimalPeopleResponseDto toMinimalPeopleDto(CeResponseDto dto){
        return MinimalPeopleResponseDto.builder()
            .name(dto.getName())
            .fatherLastName(dto.getPaternalSurname())
            .motherLastName(dto.getMaternalSurname())
            .documentNumber(dto.getDocumentNumber())
            .documentType(CE)
            .hasExternalSource(Boolean.TRUE)
            .dataSource(PersonalDataSourceEnum.MIGRATION.getDisplayName())
            .build();
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
