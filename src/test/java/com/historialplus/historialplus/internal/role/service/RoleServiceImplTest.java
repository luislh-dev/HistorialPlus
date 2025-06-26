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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

		assertNotNull(roles);
		assertEquals(2, roles.size());
		assertEquals(ROLE_ADMIN.getDisplayName(), roles.getFirst().getName());
		assertEquals(ROLE_MANAGEMENT.getDisplayName(), roles.getLast().getName());
	}

	@Test
	void findById() {
		given(this.roleRepository.findById(anyInt())).willReturn(Optional.of(this.role));

		RoleDto dto = this.roleService.findById(this.role.getId()).orElse(null);

		assertNotNull(dto);
		assertEquals(role.getId(), dto.getId());
		assertEquals(ROLE_ADMIN.getDisplayName(), dto.getName());
	}

}