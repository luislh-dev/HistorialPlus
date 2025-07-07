package com.historialplus.historialplus.internal.people.projection;

import com.historialplus.historialplus.common.enums.SexTypeEnum;

import java.util.UUID;

public interface PersonaBasicProjection {
    UUID getId();

    String getName();

    String getMaternalSurname();

    String getPaternalSurname();

    String getBirthdate();

    String getDocumentNumber();

    String getNationality();

    SexTypeProjection getSexType();

    interface SexTypeProjection {
        SexTypeEnum getName();
    }

    DocumentTypeDocument getTypeDocument();

    interface DocumentTypeDocument {
        String getName();
    }
}
