package com.historialplus.historialplus.service.fileservice;

import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import com.historialplus.historialplus.dto.filesDTOs.response.FilesResponseDto;
import com.historialplus.historialplus.entities.FileEntity;
import com.historialplus.historialplus.entities.RecordDetailEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFileService {
    List<FilesResponseDto> findAll();
    Optional<FilesResponseDto> findById(UUID id);
    FileEntity save(FileEntity fileEntity);
}