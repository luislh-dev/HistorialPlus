package com.historialplus.historialplus.typesex.service;

import com.historialplus.historialplus.typesex.dto.SexTypeResponseDto;

import java.util.List;

public interface ISexTypeService {
    List<SexTypeResponseDto> findAll();
}
