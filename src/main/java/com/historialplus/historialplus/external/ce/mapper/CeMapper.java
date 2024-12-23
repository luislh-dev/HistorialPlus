package com.historialplus.historialplus.external.ce.mapper;

import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeConstants.CE_NAME;

public class CeMapper {
    // Convertir de CeResponseDto a MinimalPeopleResponseDto
    public static MinimalPeopleResponseDto toMinimalPeopleDto(CeResponseDto dto){
        return new MinimalPeopleResponseDto(
                dto.getNames(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getDocumentNumber(),
                CE_NAME,
                null,
                true,
                "Reniec"
        );
    }
}
