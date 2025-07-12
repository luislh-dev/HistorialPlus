package com.historialplus.historialplus.internal.documenttype.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DocumentTypeDTO {
	private String id;
	private String name;
}
