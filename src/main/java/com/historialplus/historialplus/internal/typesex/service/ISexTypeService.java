package com.historialplus.historialplus.internal.typesex.service;

import com.historialplus.historialplus.internal.typesex.dto.SexTypeResponseDto;

import java.util.List;

public interface ISexTypeService {
    List<SexTypeResponseDto> findAll();
}
