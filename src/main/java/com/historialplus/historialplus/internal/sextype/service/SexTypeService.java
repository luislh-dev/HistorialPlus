package com.historialplus.historialplus.internal.sextype.service;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;

import java.util.List;

public interface SexTypeService {
    List<SexTypeResponseDto> findAll();
}
