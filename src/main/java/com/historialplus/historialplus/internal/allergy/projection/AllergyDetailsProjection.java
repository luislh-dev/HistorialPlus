package com.historialplus.historialplus.internal.allergy.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AllergyDetailsProjection extends AllergyPageProjection{
    String getSource();
    String getNotes();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    // Si necesitas el ID de la persona en los detalles
    @Value("#{target.people.id}")
    UUID getPeopleId();
}