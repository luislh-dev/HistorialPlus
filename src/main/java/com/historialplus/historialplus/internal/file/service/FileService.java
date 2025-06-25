package com.historialplus.historialplus.internal.file.service;

import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.internal.file.entites.FileEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {
    List<FilesResponseDto> findAll();
    Optional<FilesResponseDto> findById(UUID id);
    FileEntity save(FileEntity fileEntity);
}