package com.historialplus.historialplus.error.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDetail {
	private String field;
	private String message;
}
