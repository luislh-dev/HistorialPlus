package com.historialplus.historialplus.internal.allergy.dto.response;

import com.historialplus.historialplus.common.constants.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyResponseDto {
    private Integer allergyId;
    private UUID peopleId;
    private String peopleFullName;
    private String allergenSubstance;
    private String reaction;
    private SeverityLevel severity;
    private String severityDisplayName;
    private String source;
    private LocalDate recordedDate;
    private Boolean isActive;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
