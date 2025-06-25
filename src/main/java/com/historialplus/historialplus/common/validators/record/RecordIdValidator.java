package com.historialplus.historialplus.common.validators.record;

import com.historialplus.historialplus.internal.record.service.RecordService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RecordIdValidator implements ConstraintValidator<ValidRecordId, UUID> {

    private final RecordService recordService;

    @Override
    public boolean isValid(UUID recordId, ConstraintValidatorContext context) {
        return recordId != null && recordService.findById(recordId).isPresent();
    }
}
