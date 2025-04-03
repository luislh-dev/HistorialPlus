package com.historialplus.historialplus.internal.typesex.mapper;

import com.historialplus.historialplus.internal.typesex.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface ISexTypeListMapper {

	@Mapping(source = "name.displayName", target = "name")
	SexTypeResponseDto sexTypeEntityToSexTypeResponseDto(SexTypeEntity entity);
}