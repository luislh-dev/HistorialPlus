package com.historialplus.historialplus.internal.patientallergy.mapper;

import com.historialplus.historialplus.internal.patientallergy.dto.request.UpdatePatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.response.PatientAllergyResponseDto;
import com.historialplus.historialplus.internal.patientallergy.entities.PatientAllergyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientAllergyMapper {

    @Mapping(target = "allergyName", source = "allergy.name")
    @Mapping(target = "allergyCode", source = "allergy.code")
    @Mapping(target = "allergyCategory", source = "allergy.category")
    PatientAllergyResponseDto toDto(PatientAllergyEntity entity);

    List<PatientAllergyResponseDto> toDtoList(List<PatientAllergyEntity> entities);

    void updateEntityFromDto(UpdatePatientAllergyDto dto, @MappingTarget PatientAllergyEntity entity);
}
