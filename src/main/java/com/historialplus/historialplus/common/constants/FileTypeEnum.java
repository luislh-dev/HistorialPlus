package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum FileTypeEnum {
	PRESCRIPTION("Recetas"),
	MEDICAL_REPORT("Informes médicos"),
	LAB_RESULT("Resultados de laboratorio"),
	XRAY("Radiografías"),
	SCAN("Otros tipos de imágenes médicas"),
	OTHER("Otros documentos");

	private final String displayName;

	FileTypeEnum(String displayName) {
		this.displayName = displayName;
	}

}
