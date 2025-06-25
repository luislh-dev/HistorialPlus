package com.historialplus.historialplus.internal.filetype.service;

import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.mapper.FileTypeMapper;
import com.historialplus.historialplus.internal.filetype.repository.FileTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileTypeServiceImpl implements FileTypeService {

    private final FileTypeRepository fileTypeRepository;
    private final FileTypeMapper mapper;

    @Override
    public List<FileTypeDto> getAll() {
        try {
            return fileTypeRepository.findAllProjectedBy().stream()
                .map(mapper::fileTypeProjectionToFileTypeDto)
                .collect(Collectors.toList());
        } catch (Exception e){
            log.error("Error fetching file types", e);
            return List.of();
        }
    }
}
