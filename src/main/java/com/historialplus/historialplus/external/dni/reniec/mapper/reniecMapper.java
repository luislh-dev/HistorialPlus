package com.historialplus.historialplus.external.dni.reniec.mapper;

import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeConstants.DNI_NAME;

public class reniecMapper {

    // convertir de tipo reniecResponseDto a MinimalPeopleResponseDto
    public static MinimalPeopleResponseDto toMinimalPeopleDto(ReniecResponseDto reniecResponseDto) {
        return new MinimalPeopleResponseDto(
                reniecResponseDto.getNombres(),
                reniecResponseDto.getApellidoPaterno(),
                reniecResponseDto.getApellidoMaterno(),
                reniecResponseDto.getNumeroDocumento(),
                DNI_NAME,
                null,
                true,
                "Reniec"
        );
    }
}
