package com.historialplus.historialplus.dto.reniecDTO.mapper;

import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.dto.reniecDTO.ReniecResponseDto;

public class reniecMapper {

    // convertir de tipo reniecResponseDto a MinimalPeopleResponseDto
    public static MinimalPeopleResponseDto toMinimalPeopleDto(ReniecResponseDto reniecResponseDto) {
        MinimalPeopleResponseDto reniecNameDto = new MinimalPeopleResponseDto();
        reniecNameDto.setName(reniecResponseDto.getNombres());
        reniecNameDto.setFatherLastName(reniecResponseDto.getApellidoPaterno());
        reniecNameDto.setMotherLastName(reniecResponseDto.getApellidoMaterno());
        reniecNameDto.setPhone(null); // Es un campo opcional y no se encuentra en la respuesta de la reniec
        reniecNameDto.setHasExternalSource(true);
        return reniecNameDto;
    }

}
