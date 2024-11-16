package com.historialplus.historialplus.validators.state;

import com.historialplus.historialplus.service.stateservice.IStateService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class StateIdValidator implements ConstraintValidator<ValidStateId, Integer> {

    @Autowired
    private IStateService stateService;

    @Override
    public boolean isValid(Integer stateId, ConstraintValidatorContext context) {
        return stateId != null && stateService.findById(stateId).isPresent();
    }
}
