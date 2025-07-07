package com.historialplus.historialplus.internal.allergycatalog.mapper;

import com.historialplus.historialplus.common.enums.StateEnum;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogPageResponseDTO;
import com.historialplus.historialplus.internal.allergycatalog.projection.AllergyCatalogProjection;

public class Mapper {
	private Mapper() {}

	public static AllergyCatalogPageResponseDTO toAllergyCatalogPageResponseDTO(AllergyCatalogProjection input) {
		if (input == null) {
			return null;
		}

		return AllergyCatalogPageResponseDTO.builder()
				.id(input.getId())
				.code(input.getCode())
				.name(input.getName())
				.category(input.getCategory().getDisplayName())
				.status(Boolean.TRUE.equals(input.getIsActive()) ? StateEnum.ACTIVE.getDisplayName() : StateEnum.INACTIVE.getDisplayName())
				.build();
	}
}
