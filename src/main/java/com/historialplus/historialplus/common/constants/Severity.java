package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum Severity {
    LEVE("Leve"),
    MODERADA("Moderada"),
    SEVERA("Severa"),
    RIESGO_VITAL("Riesgo vital");

    private final String displayName;

    Severity(String displayName) {
        this.displayName = displayName;
    }
}
