package com.historialplus.historialplus.external.dni.reniec.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.DNI;

public class ReniecMapper {
    private ReniecMapper() {}

    public static MinimalPeopleResponseDto toMinimalPeopleDto(ReniecResponseDto reniecResponseDto) {
        MinimalPeopleResponseDto response = new MinimalPeopleResponseDto();
        response.setName(reniecResponseDto.getNombres());
        response.setFatherLastName(reniecResponseDto.getApellidoPaterno());
        response.setMotherLastName(reniecResponseDto.getApellidoMaterno());
        response.setDocumentNumber(reniecResponseDto.getNumeroDocumento());
        response.setDocumentType(DNI.getDisplayName());
        response.setHasExternalSource(Boolean.TRUE);
        response.setDataSource(PersonalDataSourceEnum.RENIEC.getDisplayName());
        return response;
    }
}
