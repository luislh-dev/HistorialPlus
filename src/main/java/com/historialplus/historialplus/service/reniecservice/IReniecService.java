package com.historialplus.historialplus.service.reniecservice;

import com.historialplus.historialplus.dto.reniecDTO.ReniecResponseDto;

import java.util.Optional;

public interface IReniecService {
    Optional<ReniecResponseDto> getPersonData(String dni);

}
