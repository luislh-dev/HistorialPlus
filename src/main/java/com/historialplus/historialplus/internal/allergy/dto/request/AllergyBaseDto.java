package com.historialplus.historialplus.internal.allergy.dto.request;

import com.historialplus.historialplus.internal.allergy.entities.AllergyEntity;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public class AllergyBaseDto {
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
