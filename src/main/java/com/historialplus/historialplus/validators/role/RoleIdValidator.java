package com.historialplus.historialplus.validators.role;

import com.historialplus.historialplus.service.roleservice.IRoleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleIdValidator implements ConstraintValidator<ValidRoleId, Integer> {

    @Autowired
    private IRoleService roleService;

    @Override
    public boolean isValid(Integer roleId, ConstraintValidatorContext context) {
        return roleId != null && roleService.findById(roleId).isPresent();
    }
}