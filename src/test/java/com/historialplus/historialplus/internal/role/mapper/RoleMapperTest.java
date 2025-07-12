package com.historialplus.historialplus.internal.role.mapper;

import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import org.junit.jupiter.api.Test;

import static com.historialplus.historialplus.common.enums.RoleEnum.ROLE_ADMIN;
import static com.historialplus.historialplus.internal.role.mapper.RoleMapper.roleEntityToRoleDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RoleMapperTest {

	@Test
	void testRoleEntityToRoleDto() {
		RoleEntity entity = RoleEntity.builder()
			.id(1)
			.name(ROLE_ADMIN)
			.build();

		RoleDto dto = roleEntityToRoleDto(entity);

		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(1);
		assertThat(dto.getName()).isEqualTo(ROLE_ADMIN.getDisplayName());
	}

}