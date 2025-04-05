package com.historialplus.historialplus.internal.filetype.service;

import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.mapper.FileTypeMapper;
import com.historialplus.historialplus.internal.filetype.repository.FileTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileTypeServiceImpl implements IFileTypeService {

    private final Logger logger = LoggerFactory.getLogger(FileTypeServiceImpl.class);
    private final FileTypeRepository fileTypeRepository;
    private final FileTypeMapper mapper;

    @Override
    public List<FileTypeDto> getAll() {
        try {
            return fileTypeRepository.findAllProjectedBy().stream()
                .map(mapper::fileTypeProjectionToFileTypeDto)
                .collect(Collectors.toList());
        } catch (Exception e){
            logger.error("Error fetching file types", e);
            return List.of();
        }
    }
}
