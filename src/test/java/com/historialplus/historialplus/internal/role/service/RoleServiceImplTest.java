package com.historialplus.historialplus.internal.role.service;

import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_ADMIN;
import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_MANAGEMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private RoleServiceImpl roleService;

	private RoleEntity role;

	@BeforeEach
	void setUp() {
		role = RoleEntity.builder().id(1).name(ROLE_ADMIN).build();
	}

	@Test
	void findAll(){
		RoleEntity role1 = RoleEntity.builder().id(2).name(ROLE_MANAGEMENT).build();
		given(this.roleRepository.findAll()).willReturn(List.of(role, role1));

		List<RoleDto> roles = roleService.findAll();

		assertThat(roles).isNotNull();
		assertThat(roles.size()).isEqualTo(2);
		assertThat(roles.getFirst().getName()).isEqualTo(ROLE_ADMIN.getDisplayName());
		assertThat(roles.getLast().getName()).isEqualTo(ROLE_MANAGEMENT.getDisplayName());
	}

	@Test
	void findById() {
		given(this.roleRepository.findById(anyInt())).willReturn(Optional.of(this.role));

		RoleDto dto = this.roleService.findById(this.role.getId()).orElse(null);

		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(this.role.getId());
		assertThat(dto.getName()).isEqualTo(ROLE_ADMIN.getDisplayName());
	}

}