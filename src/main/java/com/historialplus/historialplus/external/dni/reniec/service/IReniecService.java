package com.historialplus.historialplus.external.dni.reniec.service;

import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;

import java.util.Optional;

public interface IReniecService {
    Optional<ReniecResponseDto> getPersonData(String dni);

}
