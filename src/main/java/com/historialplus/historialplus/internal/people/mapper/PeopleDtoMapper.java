package com.historialplus.historialplus.internal.people.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.internal.documenttype.entities.DocumentTypeEntity;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.sextype.entities.SexTypeEntity;

public class PeopleDtoMapper {

    private PeopleDtoMapper() {}

    public static PeopleResponseDto toPeopleResponseDto(PeopleEntity entity) {
        return PeopleResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .paternalSurname(entity.getPaternalSurname())
            .maternalSurname(entity.getMaternalSurname())
            .birthdate(entity.getBirthdate())
            .build();
    }

    public static PeopleEntity toPeopleEntity(PeopleCreateDto dto) {
        PeopleEntity entity = new PeopleEntity();
        entity.setName(dto.getName());
        entity.setPaternalSurname(dto.getPaternalSurname());
        entity.setMaternalSurname(dto.getMaternalSurname());
        entity.setBirthdate(dto.getBirthdate());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setBloodType(dto.getBloodType());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setNationality(dto.getNationality());

        var sexType = new SexTypeEntity();
        sexType.setId(dto.getSexTypeId());
        entity.setSexType(sexType);

        var typeDocument = new DocumentTypeEntity();
        typeDocument.setId(dto.getTypeDocumentId());
        entity.setTypeDocument(typeDocument);

        return entity;
    }

    public static MinimalPeopleResponseDto toMinimalPeopleDto(PeopleEntity peopleEntity) {
        MinimalPeopleResponseDto response = new MinimalPeopleResponseDto();
        response.setName(peopleEntity.getName());
        response.setFatherLastName(peopleEntity.getPaternalSurname());
        response.setMotherLastName(peopleEntity.getMaternalSurname());
        response.setDocumentNumber(peopleEntity.getDocumentNumber());
        response.setDocumentType(peopleEntity.getTypeDocument().getName().getDisplayName());
        response.setPhone(peopleEntity.getPhone());
        response.setHasExternalSource(Boolean.FALSE);
        response.setDataSource(PersonalDataSourceEnum.INTERNAL.getDisplayName());
        return response;
    }
}
