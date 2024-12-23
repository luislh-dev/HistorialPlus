package com.historialplus.historialplus.internal.recorddetail.service;

import com.historialplus.historialplus.internal.file.service.IFileService;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.record.repository.RecordRepository;
import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateDto;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.internal.recorddetail.repository.RecordDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecordDetailServiceImpl implements IRecordDetailService {

    private final RecordDetailRepository recordDetailRepository;
    private final RecordRepository recordRepository;
    private final IFileService fileService;

    @Autowired
    public RecordDetailServiceImpl(RecordDetailRepository recordDetailRepository, RecordRepository recordRepository, IFileService fileService) {
        this.recordDetailRepository = recordDetailRepository;
        this.recordRepository = recordRepository;
        this.fileService = fileService;
    }

    @Override
    public List<RecordDetailResponseDto> findAll() {
        return recordDetailRepository.findAll().stream()
                .map(RecordDetailDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecordDetailResponseDto> findById(UUID id) {
        return recordDetailRepository.findById(id)
                .map(RecordDetailDtoMapper::toResponseDto);
    }

    @Override
    public List<RecordDetailResponseDto> findByRecordId(UUID recordId) {
        return recordDetailRepository.findByRecordId(recordId).stream()
                .map(RecordDetailDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecordDetailResponseDto save(RecordDetailCreateDto recordDetailCreateDto) {
        RecordEntity parentRecord = recordRepository.findById(recordDetailCreateDto.getRecordId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid record ID"));
        RecordDetailEntity recordDetailEntity = RecordDetailDtoMapper.toEntity(recordDetailCreateDto, parentRecord);

        RecordDetailEntity savedRecordDetail = recordDetailRepository.save(recordDetailEntity);

        // Save files associated with the record detail
        recordDetailEntity.getFiles().forEach(file -> {
            file.setRecordDetail(savedRecordDetail);
            fileService.save(file);
        });

        return RecordDetailDtoMapper.toResponseDto(savedRecordDetail);
    }

    @Override
    public void updateState(UUID id, Integer stateId) {
        RecordDetailEntity recordDetailEntity = recordDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record detail not found with the provided ID."));
        recordDetailEntity.getState().setId(stateId);
        recordDetailRepository.save(recordDetailEntity);
    }

    @Override
    public Optional<RecordDetailEntity> findEntityById(UUID id) {
        return recordDetailRepository.findById(id);
    }
}