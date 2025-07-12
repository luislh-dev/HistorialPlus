package com.historialplus.historialplus.internal.allergycatalog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AllergyCatalogPageResponseDTO {
	private UUID id;
	private String code;
	private String name;
	private String category;
	private String status;
}
