package com.historialplus.historialplus.internal.recorddetail.projection;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface RecordDetailWithFilesProjection {
    UUID getId();
    LocalDateTime getVisitDate();
    String getReason();
    String getDiagnosis();
    String getTreatment();

    HospitalProjection getHospital();
    interface HospitalProjection {
        UUID getId();
        String getName();
    }

    DoctorProjection getDoctor();
    interface DoctorProjection {
        UUID getId();
        String getName();
    }

    Set<FileDetailProjection> getFiles();
    interface FileDetailProjection {
        UUID getId();
        String getName();
        Long getSizeInBytes();
        String getUrl();
        FileTypeProjection getFileType();
        interface FileTypeProjection {
            String getName();
        }
    }
}
