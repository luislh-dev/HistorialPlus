package com.historialplus.historialplus.internal.recorddetail.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordDetailExtenseProjection {
    UUID getId();

    String getDoctorName();

    String getDoctorPaternalSurname();

    String getDoctorMaternalSurname();

    Integer getDoctorSexTypeId();

    String getHospitalName();

    LocalDateTime getVisitDate();

    String getReason();

    String getDiagnostic();

    String getTreatment();

}
