package com.historialplus.historialplus.internal.allergycatalog.mapper;

import com.historialplus.historialplus.internal.allergycatalog.dto.request.AllergyCatalogRequestDto;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogDto;
import com.historialplus.historialplus.internal.allergycatalog.entities.AllergyCatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AllergyCatalogMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AllergyCatalogEntity toEntity(AllergyCatalogRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AllergyCatalogRequestDto dto, @MappingTarget AllergyCatalogEntity entity);

    AllergyCatalogDto toDto(AllergyCatalogEntity entity);

    List<AllergyCatalogDto> toDtoList(List<AllergyCatalogEntity> entities);
}
