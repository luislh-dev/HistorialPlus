package com.historialplus.historialplus.internal.user.mapper;

import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.projection.UserListProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface UserListProjectionMapper {
	UserListProjectionMapper INSTANCE = getMapper(UserListProjectionMapper.class);

	@Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
	@Mapping(target = "state", source = "state.displayName")
	UserListResponseDto toUserListResponseDto(UserListProjection user);

	default List<String> mapRoles(String roles) {
		return roles != null ? Arrays.asList(roles.split(",")) : List.of();
	}
}
