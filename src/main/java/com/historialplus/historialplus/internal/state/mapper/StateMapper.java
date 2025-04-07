package com.historialplus.historialplus.internal.state.mapper;

import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface StateMapper {
	@Mapping(target = "name", source = "name.displayName")
	StateDto StateEntityToStateDto(StateEntity stateEntity);
}
