package com.historialplus.historialplus.internal.patientallergy.dto.response;

import com.historialplus.historialplus.common.enums.AllergyCategory;
import com.historialplus.historialplus.common.enums.Severity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PatientAllergyResponseDto {
    private UUID id;
    private String allergyName;
    private String allergyCode;
    private AllergyCategory allergyCategory;
    private Severity severity;
    private String reactionDescription;
    private LocalDate diagnosisDate;
    private boolean isActive;
}
