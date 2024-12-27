package com.historialplus.historialplus.internal.file.service;

import com.historialplus.historialplus.internal.file.mapper.FileTypeDto;
import com.historialplus.historialplus.internal.file.repository.FileTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileTypeService {

    private final FileTypeRepository fileTypeRepository;

    public List<FileTypeDto> getAll() {
        return fileTypeRepository.findAllProjectedBy().stream()
                .map(projection -> new FileTypeDto(
                        projection.getId(),
                        projection.getName().getDisplayName()
                ))
                .collect(Collectors.toList());
    }
}
