package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum DocumentTypeEnum {
	DNI("DNI"),
	CE("Carne de extranjeria");

	private final String displayName;

	DocumentTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	public static DocumentTypeEnum getDocumentTypeByName(String documentType) {
		for (DocumentTypeEnum documentTypeEnum : DocumentTypeEnum.values()) {
			if (documentTypeEnum.name().equals(documentType)) {
				return documentTypeEnum;
			}
		}
		return null;
	}
}
