package com.historialplus.historialplus.internal.state.mapper;

import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.util.dataset.state.StateTestFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StateMapperTest {
	private final StateMapper mapper = Mappers.getMapper(StateMapper.class);

	@Test
	void StateEntityToStateDto() {
		StateEntity stateEntity = StateTestFixtures.active();

		StateDto stateDto = mapper.StateEntityToStateDto(stateEntity);

		assertNotNull(stateDto);
		assertEquals(stateEntity.getId(), stateDto.getId());
		assertEquals(stateEntity.getName().getDisplayName(), stateDto.getName());
	}
}