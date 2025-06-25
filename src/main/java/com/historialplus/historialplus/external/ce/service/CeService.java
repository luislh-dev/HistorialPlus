package com.historialplus.historialplus.external.ce.service;

import com.historialplus.historialplus.external.ce.dto.CeResponseDto;

import java.util.Optional;

public interface CeService {
    Optional<CeResponseDto> getCeData(String ceeNumber);
}
