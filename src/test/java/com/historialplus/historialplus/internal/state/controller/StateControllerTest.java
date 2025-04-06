package com.historialplus.historialplus.internal.state.controller;

import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.service.IStateService;
import com.historialplus.historialplus.util.dataset.state.StateTestFixtures;
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
public class StateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IStateService stateService;

	@Test
	@WithMockUser
	void testList() throws Exception {
		List<StateEntity> states = StateTestFixtures.STATE_ENTITY_LIST;

		given(this.stateService.findAll()).willReturn(states);

		ResultActions response = mockMvc.perform(get("/api/state"));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()", is(states.size())));
	}

}