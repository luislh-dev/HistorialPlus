package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.repository.StateRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

	@Mock
	private StateRepository stateRepository;

	@InjectMocks
	private StateServiceImpl stateService;

	private StateEntity state;

	@BeforeEach
	void setUp() {
		state = StateEntity.builder().id(1).name("Activo").build();
	}

	@Test
	void findById() {
		given(stateRepository.findById(anyInt())).willReturn(Optional.of(this.state));

		StateEntity state = stateService.findById(this.state.getId()).orElse(null);

		assertThat(state).isNotNull();
		assertEquals("Activo", state.getName());
	}

	@Test
	void findByIdNotFound() {
		given(stateRepository.findById(anyInt())).willReturn(Optional.empty());

		StateEntity state = stateService.findById(this.state.getId()).orElse(null);

		assertThat(state).isNull();
	}

	@Test
	void findAll() {
		StateEntity state2 = StateEntity.builder().id(2).name("Inactivo").build();
		StateEntity state3 = StateEntity.builder().id(3).name("Eliminado").build();

		given(stateRepository.findAll()).willReturn(List.of(this.state, state2, state3));

		List<StateEntity> states = stateService.findAll();

		assertThat(states).isNotNull();
		assertThat(states).hasSize(3);
	}
}