package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.mapper.StateMapper;
import com.historialplus.historialplus.internal.state.repository.StateRepository;
import com.historialplus.historialplus.util.dataset.state.StateTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

	@Mock
	private StateRepository stateRepository;

	@Mock
	private StateMapper stateMapper;

	@InjectMocks
	private StateServiceImpl stateService;

	private StateEntity stateEntity;

	@BeforeEach
	void setUp() {
		stateEntity = StateTestFixtures.active();
	}

	@Test
	void findById() {
		given(stateRepository.findById(anyInt())).willReturn(Optional.of(this.stateEntity));

		StateEntity state = stateService.findById(this.stateEntity.getId()).orElse(null);

		assertNotNull(state);
		assertEquals(StateEnum.ACTIVE.getDisplayName(), state.getName().getDisplayName());
	}

	@Test
	void findByIdNotFound() {
		given(stateRepository.findById(anyInt())).willReturn(Optional.empty());

		StateEntity state = stateService.findById(this.stateEntity.getId()).orElse(null);

		assertNull(state);
	}

	@Test
	void findByName() {
		given(stateRepository.findByName(StateEnum.ACTIVE)).willReturn(Optional.of(this.stateEntity));

		StateEntity state = stateService.findByName(StateEnum.ACTIVE).orElse(null);

		assertThat(state).isNotNull();
		assertEquals(StateEnum.ACTIVE.getDisplayName(), state.getName().getDisplayName());
	}

	@Test
	void findByNameNotFound() {
		given(stateRepository.findByName(StateEnum.ACTIVE)).willReturn(Optional.empty());

		StateEntity state = stateService.findByName(StateEnum.ACTIVE).orElse(null);

		assertNull(state);
	}

	@Test
	void findAll() {
		given(stateRepository.findAll()).willReturn(StateTestFixtures.all());

		List<StateDto> states = stateService.findAll();

		assertNotNull(states);
		assertEquals(3, states.size());
	}
}