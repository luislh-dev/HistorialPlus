package com.historialplus.historialplus.external.dni.reniec.dto;

import lombok.Data;

@Data
public class ReniecResponseDto {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
