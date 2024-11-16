package com.historialplus.historialplus.dto.filesDTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilesCreateDto {
    @NotBlank(message = "El nombre del archivo es obligatorio.")
    private String fileName;

    @NotBlank(message = "La URL del archivo es obligatoria.")
    private String fileUrl;

    @NotNull(message = "El tipo de archivo es obligatorio.")
    private Integer fileTypeId;
}
