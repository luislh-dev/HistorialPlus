package com.historialplus.historialplus.common.validators.document;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DocumentValidator implements ConstraintValidator<DocumentValid, String> {

	private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]?$");
	private static final Pattern PASAPORTE_PATTERN = Pattern.compile("^[A-Za-z0-9]{6,12}$");
	private static final Pattern CEE_PATTERN = Pattern.compile("^[A-Za-z]{2}[0-9]{6,8}$");

	@Override public boolean isValid(String document, ConstraintValidatorContext context) {
		if (document == null || document.isEmpty()) {
			return false;
		}

		return isValidDNI(document) || isValidPasaporte(document) || isValidCEE(document);
	}

	private boolean isValidDNI(String document) {
		return DNI_PATTERN.matcher(document).matches();
	}

	private boolean isValidPasaporte(String document) {
		return PASAPORTE_PATTERN.matcher(document).matches();
	}

	private boolean isValidCEE(String document) {
		return CEE_PATTERN.matcher(document).matches();
	}
}
