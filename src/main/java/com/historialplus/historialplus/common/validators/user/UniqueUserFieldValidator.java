package com.historialplus.historialplus.common.validators.user;

import com.historialplus.historialplus.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserFieldValidator implements ConstraintValidator<UniqueUserField, String> {

    @Autowired
    private UserRepository userRepository;

    private UserFieldName fieldName;

    @Override
    public void initialize(UniqueUserField constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return switch (fieldName) {
            case EMAIL -> !userRepository.existsByEmail(value);
            case USERNAME -> !userRepository.existsByUsername(value);
        };
    }
}
