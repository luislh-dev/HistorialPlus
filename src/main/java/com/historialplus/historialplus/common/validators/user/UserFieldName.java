package com.historialplus.historialplus.common.validators.user;

import lombok.Getter;

@Getter
public enum UserFieldName {
    EMAIL("email"),
    USERNAME("username");

    private final String fieldName;

    UserFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
