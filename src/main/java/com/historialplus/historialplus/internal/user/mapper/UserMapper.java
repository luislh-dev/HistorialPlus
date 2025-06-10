package com.historialplus.historialplus.internal.user.mapper;

import com.historialplus.historialplus.common.constants.RoleEnum;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.projection.UserListProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Primary
@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
	@Mapping(target = "state", source = "state.displayName")
	UserListResponseDto toUserListResponseDto(UserListProjection user);

	default List<String> mapRoles(String roles) {
		return roles != null ? Arrays.stream(roles.split(","))
			.map(RoleEnum::getRoleByName)
			.filter(Optional::isPresent)
			.map(o -> o.get().getDisplayName())
			.toList()
			: List.of();
	}

	@Mapping(target = "name", source = "username")
	@Mapping(target = "roleId", expression = "java(mapRoleIds(userEntity.getRoleEntities()))")
	@Mapping(target = "stateId", source = "state.id")
	UserResponseDto userEntityToUserResponseDto(UserEntity userEntity);

	default List<Integer> mapRoleIds(List<RoleEntity> roleEntities) {
		return roleEntities != null
			? roleEntities.stream()
				.map(RoleEntity::getId)
				.toList()
			: List.of();
	}
}
