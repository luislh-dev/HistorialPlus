package com.historialplus.historialplus.internal.recorddetail.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordDetailProjection {
    UUID getId();

    String getReason();

    LocalDateTime getVisitDate();

    String getHospitalName();

    String getSexTypeName();

    String getDoctorFullName();

}
