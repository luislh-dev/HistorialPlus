package com.historialplus.historialplus.internal.allergy.projection;

import org.springframework.beans.factory.annotation.Value;

public interface AllergyPageProjection {

    @Value("#{target.severity.displayName}")
    String getSeverityDisplayName();

    @Value("#{target.people.name + ' ' + target.people.paternalSurname + ' ' + target.people.maternalSurname}")
    String getPeopleFullName();
}
