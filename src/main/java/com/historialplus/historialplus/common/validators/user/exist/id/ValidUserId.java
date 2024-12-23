package com.historialplus.historialplus.common.validators.user.exist.id;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserId {
    String message() default "Usuario no encontrado";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
