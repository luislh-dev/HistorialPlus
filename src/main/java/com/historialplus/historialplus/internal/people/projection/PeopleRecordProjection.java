package com.historialplus.historialplus.internal.people.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public record PeopleRecordProjection(
        UUID id,
        String documentNumber,
        String fullName,
        Integer totalVisits,
        LocalDateTime lastVisitDate,
        String lastVisitHospitalName
) {}