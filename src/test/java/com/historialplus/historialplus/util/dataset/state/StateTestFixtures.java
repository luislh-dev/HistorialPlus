package com.historialplus.historialplus.util.dataset.state;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.util.List;

public class StateTestFixtures {

	public static StateEntity active() {
		return StateEntity.builder()
			.name(StateEnum.ACTIVE)
			.build();
	}

	public static StateEntity inactive()  {
		return StateEntity.builder()
			.name(StateEnum.INACTIVE)
			.build();
	}

	public static StateEntity deleted() {
		return StateEntity.builder()
			.name(StateEnum.DELETED)
			.build();
	}

	public static List<StateEntity> all() {
		return List.of(
			active(),
			inactive(),
			deleted()
		);
	}
}
