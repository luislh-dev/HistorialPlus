package com.historialplus.historialplus.util.roles;

import com.historialplus.historialplus.entities.RoleEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public final class RoleTransformer {

    private RoleTransformer() {
        // Private constructor to prevent instantiation
    }

    public static @NotNull List<RoleEntity> transformRoles(List<RoleEntity> roles) {
        return roles.stream()
                .peek(role -> {
                    String name = role.getName().replace("ROLE_", "").toLowerCase();
                    name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    role.setName(name);
                })
                .collect(Collectors.toList());
    }
}