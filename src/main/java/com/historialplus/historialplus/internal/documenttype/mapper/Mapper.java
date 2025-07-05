package com.historialplus.historialplus.internal.documenttype.mapper;

import com.historialplus.historialplus.internal.documenttype.dto.DocumentTypeDTO;
import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;

public class Mapper {
	private Mapper() {}

	public static DocumentTypeDTO toDTO(DocumentTypeProjection projection) {
		return  DocumentTypeDTO.builder()
			.id(projection.getId().name())
			.name(projection.getName())
			.build();
	}
}
