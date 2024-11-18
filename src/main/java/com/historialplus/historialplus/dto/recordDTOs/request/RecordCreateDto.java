package com.historialplus.historialplus.dto.recordDTOs.request;

import com.historialplus.historialplus.dto.recordDetailDTOs.request.RecordDetailCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordCreateDto {
    @NotNull(message = "El número de documento es requerido")
    private String documentNumber;

    private List<RecordDetailCreateDto> details;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public List<RecordDetailCreateDto> getDetails() {
        return details;
    }
}