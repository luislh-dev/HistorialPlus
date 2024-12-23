package com.historialplus.historialplus.file.service;

import com.historialplus.historialplus.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.file.entites.FileEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFileService {
    List<FilesResponseDto> findAll();
    Optional<FilesResponseDto> findById(UUID id);
    FileEntity save(FileEntity fileEntity);
}