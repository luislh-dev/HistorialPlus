package com.historialplus.historialplus.dto.recordDTOs.request;

import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordCreateDto {
    @NotNull(message = "El ID del paciente es obligatorio.")
    private String patientId;

    @NotBlank(message = "La descripción de la consulta es obligatoria.")
    private String description;

    @NotNull(message = "Se requiere al menos un archivo.")
    private List<FilesCreateDto> files;
}
