package com.historialplus.historialplus.internal.people.presenters;

import com.historialplus.historialplus.internal.people.projection.PeopleRecordProjection;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PeopleRecordPresenter(
        UUID id,
        String documentNumber,
        String fullName,
        Integer totalVisits,
        String lastVisitDate,
        String lastVisitHospitalName
) {
    public static PeopleRecordPresenter fromProjection(PeopleRecordProjection projection) {
        return new PeopleRecordPresenter(
                projection.id(),
                projection.documentNumber(),
                projection.fullName(),
                projection.totalVisits(),
                projection.lastVisitDate() != null
                        ? projection.lastVisitDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                        : "-",
                projection.lastVisitHospitalName() != null
                        ? projection.lastVisitHospitalName()
                        : "-"
        );
    }
}