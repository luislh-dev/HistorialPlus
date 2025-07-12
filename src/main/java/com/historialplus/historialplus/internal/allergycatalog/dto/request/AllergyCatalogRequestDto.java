package com.historialplus.historialplus.internal.allergycatalog.dto.request;

import com.historialplus.historialplus.common.enums.AllergyCategory;
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
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String name;

    @Size(max = 250, message = "La descripción no puede exceder los 250 caracteres.")
    private String description;

    @NotNull(message = "La categoría es obligatoria.")
    private AllergyCategory category;
}
