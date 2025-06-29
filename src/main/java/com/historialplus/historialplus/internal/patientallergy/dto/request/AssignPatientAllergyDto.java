package com.historialplus.historialplus.internal.patientallergy.dto.request;

import com.historialplus.historialplus.common.constants.Severity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignPatientAllergyDto {
    @NotNull(message = "El ID del historial médico es obligatorio.")
    private UUID recordId;

    @NotNull(message = "El ID de la alergia del catálogo es obligatorio.")
    private UUID allergyCatalogId;

    @NotNull(message = "El nivel de severidad es obligatorio.")
    private Severity severity;

    private String reactionDescription;
    private LocalDate diagnosisDate;
}
