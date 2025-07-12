package com.historialplus.historialplus.internal.state.controller;

import com.historialplus.historialplus.common.enums.StateEnum;
import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.service.StateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StateController.class)
class StateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StateService stateService;

	@Test
	@WithMockUser
	void testList() throws Exception {
		List<StateDto> states = List.of(
				StateDto.builder().name(StateEnum.ACTIVE.getDisplayName()).build(),
				StateDto.builder().name(StateEnum.INACTIVE.getDisplayName()).build(),
				StateDto.builder().name(StateEnum.DELETED.getDisplayName()).build()
		);

		given(this.stateService.findAll()).willReturn(states);

		ResultActions response = mockMvc.perform(get("/api/state"));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()", is(states.size())));
	}

}