package com.historialplus.historialplus.internal.recorddetail.projection;

import com.historialplus.historialplus.common.constants.SexTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordDetailExtenseProjection {
    UUID getId();

    String getDoctorName();

    String getDoctorPaternalSurname();

    String getDoctorMaternalSurname();

    SexTypeEnum getDoctorSexTypeName();

    String getHospitalName();

    LocalDateTime getVisitDate();

    String getReason();

    String getDiagnostic();

    String getTreatment();

}
