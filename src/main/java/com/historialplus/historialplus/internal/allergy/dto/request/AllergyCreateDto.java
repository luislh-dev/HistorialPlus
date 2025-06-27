package com.historialplus.historialplus.internal.allergy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AllergyCreateDto extends AllergyBaseDto{
    @NotNull(message = "El ID de la persona es obligatorio")
    private UUID peopleId;

    @NotBlank(message = "La sustancia alergénica es obligatoria")
    @Override
    public String getAllergenSubstance() {
        return super.getAllergenSubstance();
    }
}
