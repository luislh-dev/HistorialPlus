package com.historialplus.historialplus.util.dataset.state;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.util.List;

public class StateTestFixtures {

	public static final StateEntity STATE_ENTITY_ACTIVE = StateEntity.builder()
			.id(1)
			.name(StateEnum.ACTIVE)
			.build();

	public static final StateEntity STATE_ENTITY_INACTIVE = StateEntity.builder()
			.id(2)
			.name(StateEnum.INACTIVE)
			.build();

	public static final StateEntity STATE_ENTITY_DELETED = StateEntity.builder()
			.id(3)
			.name(StateEnum.DELETED)
			.build();

	public static final List<StateEntity> STATE_ENTITY_LIST = List.of(
		STATE_ENTITY_ACTIVE,
		STATE_ENTITY_INACTIVE,
		STATE_ENTITY_DELETED
	);
}
