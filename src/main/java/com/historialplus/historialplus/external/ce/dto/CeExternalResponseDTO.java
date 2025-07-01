package com.historialplus.historialplus.external.ce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CeExternalResponseDTO {
	@JsonProperty("nombres")
	private String name;

	@JsonProperty("apellido_paterno")
	private String paternalSurname;

	@JsonProperty("apellido_materno")
	private String maternalSurname;

	@JsonProperty("numero")
	private String documentNumber;
}
