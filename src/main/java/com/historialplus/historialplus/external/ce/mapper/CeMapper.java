package com.historialplus.historialplus.external.ce.mapper;

import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;

public class CeMapper {
    // Convertir de CeeResponseDto a MinimalPeopleResponseDto
    public static MinimalPeopleResponseDto toMinimalPeopleResponseDto(CeResponseDto dto){
        MinimalPeopleResponseDto minimalPeopleResponseDto = new MinimalPeopleResponseDto();
        minimalPeopleResponseDto.setName(dto.getNames());
        minimalPeopleResponseDto.setFatherLastName(dto.getApellidoPaterno());
        minimalPeopleResponseDto.setMotherLastName(dto.getApellidoMaterno());
        minimalPeopleResponseDto.setPhone(null); // Es un campo opcional y no se encuentra en la respuesta de la cee
        minimalPeopleResponseDto.setHasExternalSource(false);
        return minimalPeopleResponseDto;
    }

}
