package com.historialplus.historialplus.internal.sextype.mapper;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.entities.SexTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface ISexTypeListMapper {

	@Mapping(source = "name.displayName", target = "name")
	SexTypeResponseDto sexTypeEntityToSexTypeResponseDto(SexTypeEntity entity);
}