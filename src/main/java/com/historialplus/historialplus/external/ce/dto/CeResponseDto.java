package com.historialplus.historialplus.external.ce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CeResponseDto {
    private String name;
    private String maternalSurname;
    private String paternalSurname;
    private String documentNumber;
}