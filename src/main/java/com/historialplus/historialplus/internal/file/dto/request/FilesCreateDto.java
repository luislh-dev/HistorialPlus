package com.historialplus.historialplus.internal.file.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FilesCreateDto {
    @NotNull(message = "El ID del detalle del registro es requerido")
    private UUID recordDetailId;

    @NotNull(message = "El tipo de archivo es requerido")
    private Integer fileTypeId;

    @NotNull(message = "El nombre del archivo es requerido")
    private String name;

    @NotNull(message = "La URL del archivo es requerida")
    private String url;

}