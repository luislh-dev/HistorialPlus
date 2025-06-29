package com.historialplus.historialplus.internal.patientallergy.dto.request;

import com.historialplus.historialplus.common.constants.Severity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientAllergyDto {
    @NotNull(message = "El nivel de severidad es obligatorio.")
    private Severity severity;
    private String reactionDescription;
    private LocalDate diagnosisDate;
}
