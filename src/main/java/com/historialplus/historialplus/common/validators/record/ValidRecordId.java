package com.historialplus.historialplus.common.validators.record;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RecordIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRecordId {
    String message() default "Registro médico no encontrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
