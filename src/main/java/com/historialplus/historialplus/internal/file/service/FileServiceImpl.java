package com.historialplus.historialplus.internal.file.service;

import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.internal.file.dto.request.FilesCreateDto;
import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.mapper.FilesDtoMapper;
import com.historialplus.historialplus.internal.file.repository.FileRepository;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.service.RecordDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final RecordDetailService recordDetailService;
    private final FileRepository fileRepository;

    @Override
    public List<FilesResponseDto> findAll() {
        return fileRepository.findAll().stream()
                .map(FilesDtoMapper::toResponseDto)
            .toList();
    }

    @Override
    public Optional<FilesResponseDto> findById(UUID id) {
        return fileRepository.findById(id)
                .map(FilesDtoMapper::toResponseDto);
    }

    @Override public FilesResponseDto save(FilesCreateDto dto) {
        RecordDetailEntity parentDetail = recordDetailService.findEntityById(dto.getRecordDetailId())
            .orElseThrow(() -> new NotFoundException("Invalid record detail ID"));

        FileEntity fileEntity = FilesDtoMapper.toEntity(dto, parentDetail);
        FileEntity savedFile = fileRepository.save(fileEntity);
        return FilesDtoMapper.toResponseDto(savedFile);
    }

}