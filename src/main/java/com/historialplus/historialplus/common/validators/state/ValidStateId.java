package com.historialplus.historialplus.common.validators.state;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StateIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStateId {
    String message() default "Estado no encontrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

