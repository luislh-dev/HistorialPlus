package com.historialplus.historialplus.internal.allergy.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface AllergyPageProjection {
    Integer getAllergyId();
    String getAllergenSubstance();
    String getReaction();

    @Value("#{target.severity.displayName}")
    String getSeverityDisplayName();

    LocalDate getRecordedDate();

    Boolean getIsActive();

    @Value("#{target.people.name + ' ' + target.people.paternalSurname + ' ' + target.people.maternalSurname}")
    String getPeopleFullName();
}
