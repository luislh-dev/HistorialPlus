package com.historialplus.historialplus.common.validators.role;

import com.historialplus.historialplus.internal.role.service.RoleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleIdValidator implements ConstraintValidator<ValidRoleId, Integer> {

    private final RoleService roleService;

    @Override
    public boolean isValid(Integer roleId, ConstraintValidatorContext context) {
        return roleId != null && roleService.findById(roleId).isPresent();
    }
}