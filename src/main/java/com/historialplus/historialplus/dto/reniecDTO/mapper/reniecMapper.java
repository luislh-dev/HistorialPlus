package com.historialplus.historialplus.dto.reniecDTO.mapper;

import com.historialplus.historialplus.dto.reniecDTO.ReniecResponseDto;
import com.historialplus.historialplus.people.dto.response.MinimalPeopleResponseDto;

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
