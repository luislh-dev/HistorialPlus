package com.historialplus.historialplus.internal.role.dto;

import lombok.Getter;

@Getter
public class RoleDto {
    private final Integer id;
    private final String name;

    public RoleDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
