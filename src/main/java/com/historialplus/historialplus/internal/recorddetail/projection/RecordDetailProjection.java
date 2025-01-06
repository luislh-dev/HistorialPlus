package com.historialplus.historialplus.internal.recorddetail.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface RecordDetailProjection {
    UUID getId();
    String getReason();
    LocalDateTime getVisitDate();

    @Value("#{target.hospital.name}")
    String getHospitalName();

    @Value("#{target.doctor.person.sexType.id}")
    Integer getSexTypeId();

    @Value("#{target.doctor.person.name + ' ' + target.doctor.person.paternalSurname}")
    String getDoctorFullName();

    Set<FileProjection> getFiles();

    interface FileProjection {
        String getName();
        Long getSizeInBytes();
        String getUrl();
        String getMimeType();
        @Value("#{target.fileType.name.displayName}")
        String getTypeName();
    }
}
