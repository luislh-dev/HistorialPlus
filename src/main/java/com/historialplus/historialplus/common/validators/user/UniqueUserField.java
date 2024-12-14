package com.historialplus.historialplus.common.validators.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserField {
    String message() default "This field must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    UserFieldName fieldName();
}

