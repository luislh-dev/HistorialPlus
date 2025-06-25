package com.historialplus.historialplus.internal.file.service;

import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.mapper.FilesDtoMapper;
import com.historialplus.historialplus.internal.file.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public List<FilesResponseDto> findAll() {
        return fileRepository.findAll().stream()
                .map(FilesDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FilesResponseDto> findById(UUID id) {
        return fileRepository.findById(id)
                .map(FilesDtoMapper::toResponseDto);
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

}