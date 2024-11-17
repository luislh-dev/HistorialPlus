package com.historialplus.historialplus.validators.user;

import com.historialplus.historialplus.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserIdValidator implements ConstraintValidator<ValidUserId, UUID> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UUID userId, ConstraintValidatorContext context) {
        return userId != null && userRepository.findById(userId).isPresent();
    }
}
