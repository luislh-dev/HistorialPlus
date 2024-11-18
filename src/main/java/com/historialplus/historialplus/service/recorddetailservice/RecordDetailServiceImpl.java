package com.historialplus.historialplus.service.recorddetailservice;

import com.historialplus.historialplus.dto.recordDetailDTOs.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.dto.recordDetailDTOs.request.RecordDetailCreateDto;
import com.historialplus.historialplus.dto.recordDetailDTOs.response.RecordDetailResponseDto;
import com.historialplus.historialplus.entities.RecordDetailEntity;
import com.historialplus.historialplus.repository.RecordDetailRepository;
import com.historialplus.historialplus.repository.RecordRepository;
import com.historialplus.historialplus.service.fileservice.IFileService;
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
        return null;
    }

    @Override
    public void updateState(UUID id, Integer stateId) {
        RecordDetailEntity recordDetailEntity = recordDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record detail not found with the provided ID."));
        recordDetailEntity.setStateId(stateId);
        recordDetailRepository.save(recordDetailEntity);
    }

    @Override
    public Optional<RecordDetailEntity> findEntityById(UUID id) {
        return recordDetailRepository.findById(id);
    }
}