package com.historialplus.historialplus.internal.sextype.controller;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.service.ISexTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.historialplus.historialplus.common.constants.SexTypeEnum.FEMALE;
import static com.historialplus.historialplus.common.constants.SexTypeEnum.MALE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SexTypeController.class)
class SexTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ISexTypeService service;

	@Test
	@WithMockUser
	void findAllTest() throws Exception {
		List<SexTypeResponseDto> dtos = List.of(
			SexTypeResponseDto.builder().id(1).name(MALE.getDisplayName()).build(),
			SexTypeResponseDto.builder().id(2).name(FEMALE.getDisplayName()).build()
		);

		given(this.service.findAll()).willReturn(dtos);

		ResultActions response = mockMvc.perform(get("/api/sexType"));

		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.size()").value(dtos.size()));
	}

}