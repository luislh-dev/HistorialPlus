package com.historialplus.historialplus.internal.allergycatalog.service;

import com.historialplus.historialplus.internal.allergycatalog.dto.request.AllergyCatalogRequestDto;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AllergyCatalogService {
    AllergyCatalogDto create(AllergyCatalogRequestDto dto);
    AllergyCatalogDto findById(UUID id);
    Page<AllergyCatalogDto> findAllActive(Pageable pageable);
    AllergyCatalogDto update(UUID id, AllergyCatalogRequestDto dto);
    AllergyCatalogDto deactivate(UUID id);
    AllergyCatalogDto reactivate(UUID id);
}
