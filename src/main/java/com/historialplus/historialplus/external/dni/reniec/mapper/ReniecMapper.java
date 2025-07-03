package com.historialplus.historialplus.external.dni.reniec.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.DNI;

public class ReniecMapper {
    private ReniecMapper() {}

    public static MinimalPeopleResponseDto toMinimalPeopleDto(ReniecResponseDto reniecResponseDto) {
        return MinimalPeopleResponseDto.builder()
            .documentNumber(reniecResponseDto.getNumeroDocumento())
            .documentType(DNI)
            .name(reniecResponseDto.getNombres())
            .fatherLastName(reniecResponseDto.getApellidoPaterno())
            .motherLastName(reniecResponseDto.getApellidoMaterno())
            .hasExternalSource(Boolean.TRUE)
            .dataSource(PersonalDataSourceEnum.RENIEC.getDisplayName())
            .build();
    }
}
