package com.historialplus.historialplus.common.enums;

import lombok.Getter;

@Getter
public enum AllergyCategory {
    MEDICAMENTO("Medicamento"),
    ALIMENTO("Alimento"),
    AMBIENTAL("Ambiental"),
    INSECTO("Insecto"),
    OTRO("Otro");

    private final String displayName;

    AllergyCategory(String displayName) {
        this.displayName = displayName;
    }
}
