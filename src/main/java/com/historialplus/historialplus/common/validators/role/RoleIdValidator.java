package com.historialplus.historialplus.common.validators.role;

import com.historialplus.historialplus.internal.role.service.RoleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleIdValidator implements ConstraintValidator<ValidRoleId, Integer> {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean isValid(Integer roleId, ConstraintValidatorContext context) {
        return roleId != null && roleService.findById(roleId).isPresent();
    }
}