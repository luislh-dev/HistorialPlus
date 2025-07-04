package com.historialplus.historialplus.internal.file.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilesCreateDto {
    @NotNull(message = "El tipo de archivo es requerido")
    private Integer fileTypeId;

    @NotNull(message = "El nombre del archivo es requerido")
    private String name;

    @NotNull(message = "La URL del archivo es requerida")
    private String url;

    public FilesCreateDto(Integer fileTypeId, String name, String url) {
        this.fileTypeId = fileTypeId;
        this.name = name;
        this.url = url;
    }
}