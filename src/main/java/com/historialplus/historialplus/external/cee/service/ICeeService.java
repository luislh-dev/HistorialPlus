package com.historialplus.historialplus.external.cee.service;

import com.historialplus.historialplus.external.cee.dto.CeeResponseDto;

import java.util.Optional;

public interface ICeeService {
    Optional<CeeResponseDto> getCeeData(String ceeNumber);
}
