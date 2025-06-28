package com.historialplus.historialplus.common.constants;

import lombok.Getter;

@Getter
public enum SeverityLevel {
    MILD("Leve"),
    MODERATE("Moderada"),
    SEVERE("Severa"),
    LIFE_THREATENING("Amenaza la vida");

    private final String displayName;

    SeverityLevel(String displayName) {
        this.displayName = displayName;
    }
}
