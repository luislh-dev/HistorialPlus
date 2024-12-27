package com.historialplus.historialplus.internal.recorddetail.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordDetailProjection {
    UUID getId();

    LocalDateTime getVisitDate();

    String getReason();

    HospitalProjection getHospital();

    DoctorProjection getDoctor();

    interface HospitalProjection {
        String getName();
    }

    interface DoctorProjection {
        String getName();
    }
}
