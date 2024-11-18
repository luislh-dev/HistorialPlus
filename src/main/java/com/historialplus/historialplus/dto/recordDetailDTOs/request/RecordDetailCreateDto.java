package com.historialplus.historialplus.dto.recordDetailDTOs.request;

import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RecordDetailCreateDto {
    @NotNull(message = "La descripción es requerida")
    private String description;

    private Integer stateId;

    @NotNull(message = "El ID del record es requerido")
    private UUID recordId;

    private List<FilesCreateDto> files;

    public RecordDetailCreateDto(String description, Integer stateId, UUID recordId, List<FilesCreateDto> files) {
        this.description = description;
        this.stateId = stateId;
        this.recordId = recordId;
        this.files = files;
    }
}