package com.historialplus.historialplus.internal.file.service;

import com.historialplus.historialplus.internal.file.dto.request.FilesCreateDto;
import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {
    List<FilesResponseDto> findAll();
    Optional<FilesResponseDto> findById(UUID id);
    FilesResponseDto save(FilesCreateDto dto);
}