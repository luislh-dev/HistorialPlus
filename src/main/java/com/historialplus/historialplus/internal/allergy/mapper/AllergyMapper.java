package com.historialplus.historialplus.internal.allergy.mapper;

import com.historialplus.historialplus.internal.allergy.dto.request.AllergyCreateDto;
import com.historialplus.historialplus.internal.allergy.dto.request.AllergyUpdateDto;
import com.historialplus.historialplus.internal.allergy.dto.response.AllergyResponseDto;
import com.historialplus.historialplus.internal.allergy.entities.AllergyEntity;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AllergyMapper {
    // Mapea AllergyCreateDto a AllergyEntity.
    @Mapping(target = "people", ignore = true)
    @Mapping(target = "allergyId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    AllergyEntity toEntity(AllergyCreateDto dto);

    // Mapea AllergyUpdateDto a una AllergyEntity existente
    @Mapping(target = "allergyId", ignore = true)
    @Mapping(target = "people", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AllergyUpdateDto dto, @MappingTarget AllergyEntity entity);

    // Mapea AllergyEntity a AllergyResponseDto
    @Mapping(target = "peopleId", source = "people.id")
    @Mapping(target = "peopleFullName", expression = "java(allergyEntityToPeopleFullName(entity.getPeople()))")
    @Mapping(target = "severityDisplayName", expression = "java(entity.getSeverity() != null ? entity.getSeverity().getDisplayName() : null)")
    AllergyResponseDto toResponseDto(AllergyEntity entity);

    // Método helper para construir el nombre completo
    default String allergyEntityToPeopleFullName(PeopleEntity people) {
        if (people == null) {
            return null;
        }
        // Construye el nombre completo: Nombre ApellidoPaterno ApellidoMaterno
        StringBuilder fullName = new StringBuilder();
        if (people.getName() != null) {
            fullName.append(people.getName());
        }
        if (people.getPaternalSurname() != null) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(people.getPaternalSurname());
        }
        if (people.getMaternalSurname() != null) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(people.getMaternalSurname());
        }
        return fullName.toString().trim();
    }
}
