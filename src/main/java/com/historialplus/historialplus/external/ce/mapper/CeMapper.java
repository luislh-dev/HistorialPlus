package com.historialplus.historialplus.external.ce.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.CE;

public class CeMapper {
    public static MinimalPeopleResponseDto toMinimalPeopleDto(CeResponseDto dto){
        MinimalPeopleResponseDto response = new MinimalPeopleResponseDto();
        response.setName(dto.getNames());
        response.setFatherLastName(dto.getApellidoPaterno());
        response.setMotherLastName(dto.getApellidoMaterno());
        response.setDocumentNumber(dto.getDocumentNumber());
        response.setDocumentType(CE.getDisplayName());
        response.setHasExternalSource(Boolean.TRUE);
        response.setDataSource(PersonalDataSourceEnum.RENIEC.getDisplayName());
        return response;
    }
}
