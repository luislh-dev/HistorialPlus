package com.historialplus.historialplus.internal.hospital.dto.response;

import lombok.Data;

@Data
public class HospitalPageResponseDto {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String ruc;
	private String stateName;
}
