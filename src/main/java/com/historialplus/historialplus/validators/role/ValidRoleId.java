package com.historialplus.historialplus.validators.role;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleId {
    String message() default "Rol no encontrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
