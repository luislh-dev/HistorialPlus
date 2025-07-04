package com.historialplus.historialplus.internal.people.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonBasicDTO {
	private UUID id;
	private String name;
	private String paternalSurname;
	private String maternalSurname;
	private String documentNumber;
	private String documentType;
	private String birthdate;
	private String nationality;
	private String sexType;
}
