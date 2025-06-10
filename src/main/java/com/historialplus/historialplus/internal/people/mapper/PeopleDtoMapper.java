package com.historialplus.historialplus.internal.people.mapper;

import com.historialplus.historialplus.common.constants.PersonalDataSourceEnum;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;

public class PeopleDtoMapper {

    private PeopleDtoMapper() {}

    public static PeopleResponseDto toPeopleResponseDto(PeopleEntity entity) {
        return new PeopleResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getPaternalSurname(),
                entity.getMaternalSurname(),
                entity.getBirthdate(),
                entity.getDocumentNumber(),
                entity.getBloodType(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getNationality(),
                entity.getSexType().getName().getDisplayName(),
                entity.getTypeDocument().getName().getDisplayName()
        );
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

        var typeDocument = new TypeDocumentEntity();
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
