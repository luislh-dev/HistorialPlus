package com.historialplus.historialplus.internal.role.controller;

import com.historialplus.historialplus.internal.role.dto.RoleDto;
import com.historialplus.historialplus.internal.role.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.historialplus.historialplus.common.enums.RoleEnum.ROLE_ADMIN;
import static com.historialplus.historialplus.common.enums.RoleEnum.ROLE_DOCTOR;
import static com.historialplus.historialplus.common.enums.RoleEnum.ROLE_MANAGEMENT;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoleController.class)
class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleService roleService;

	@Test
	@WithMockUser
	void getAllRoles() throws Exception {
		List<RoleDto> roles = List.of(
				RoleDto.builder().id(1).name(ROLE_ADMIN.name()).build(),
				RoleDto.builder().id(2).name(ROLE_MANAGEMENT.name()).build(),
				RoleDto.builder().id(3).name(ROLE_DOCTOR.name()).build()
		);
		given(this.roleService.findAll()).willReturn(roles);

		ResultActions response = mockMvc.perform(get("/api/roles"));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()").value(roles.size()));
	}
}