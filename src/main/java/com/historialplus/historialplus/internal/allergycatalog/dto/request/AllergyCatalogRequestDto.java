package com.historialplus.historialplus.internal.allergycatalog.dto.request;

import com.historialplus.historialplus.common.constants.AllergyCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyCatalogRequestDto {
    @NotBlank(message = "El nombre de la alergia es obligatorio.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String name;

    private String description;

    @NotNull(message = "La categoría es obligatoria.")
    private AllergyCategory category;
}
