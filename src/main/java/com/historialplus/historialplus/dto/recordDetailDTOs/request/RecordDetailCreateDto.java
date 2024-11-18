package com.historialplus.historialplus.dto.recordDetailDTOs.request;

import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDetailCreateDto {
    @NotNull(message = "La descripción es requerida")
    private String description;

    private Integer stateId;

    private List<FilesCreateDto> files;

    public RecordDetailCreateDto(String description, Integer stateId, List<FilesCreateDto> files) {
        this.description = description;
        this.stateId = stateId;
        this.files = files;
    }
}