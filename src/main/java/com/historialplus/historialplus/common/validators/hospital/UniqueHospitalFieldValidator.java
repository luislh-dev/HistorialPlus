package com.historialplus.historialplus.common.validators.hospital;

import com.historialplus.historialplus.hospital.repository.HospitalRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueHospitalFieldValidator implements ConstraintValidator<UniqueHospitalField, String> {

    @Autowired
    private HospitalRepository hospitalRepository;

    private String fieldName;

    @Override
    public void initialize(UniqueHospitalField constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return switch (fieldName) {
            case "email" -> !hospitalRepository.existsByEmail(value);
            case "name" -> !hospitalRepository.existsByName(value);
            case "phone" -> !hospitalRepository.existsByPhone(value);
            case "ruc" -> !hospitalRepository.existsByRuc(value);
            default -> throw new IllegalArgumentException("Campo no soportado: " + fieldName);
        };
    }
}