package com.historialplus.historialplus.internal.allergy.dto.request;

import com.historialplus.historialplus.internal.allergy.entities.AllergyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyCreateDto {
    @NotNull(message = "El ID de la persona es obligatorio")
    private UUID peopleId;

    @NotBlank(message = "La sustancia alergénica es obligatoria")
    @Size(max = 100, message = "La sustancia alergénica no puede exceder 100 caracteres")
    private String allergenSubstance;

    @Size(max = 1000, message = "La reacción no puede exceder 1000 caracteres")
    private String reaction;

    private AllergyEntity.SeverityLevel severity;

    @Size(max = 50, message = "La fuente no puede exceder 50 caracteres")
    private String source;

    private LocalDate recordedDate;

    @Size(max = 1000, message = "Las notas no pueden exceder 1000 caracteres")
    private String notes;
}
