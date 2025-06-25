package com.historialplus.historialplus.common.validators.record;

import com.historialplus.historialplus.internal.record.service.RecordService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class RecordIdValidator implements ConstraintValidator<ValidRecordId, UUID> {
    @Autowired
    private RecordService recordService;

    @Override
    public boolean isValid(UUID recordId, ConstraintValidatorContext context) {
        return recordId != null && recordService.findById(recordId).isPresent();
    }
}
