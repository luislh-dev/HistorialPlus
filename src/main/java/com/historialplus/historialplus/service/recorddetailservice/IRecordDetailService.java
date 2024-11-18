package com.historialplus.historialplus.service.recorddetailservice;

import com.historialplus.historialplus.dto.recordDetailDTOs.request.RecordDetailCreateDto;
import com.historialplus.historialplus.dto.recordDetailDTOs.response.RecordDetailResponseDto;
import com.historialplus.historialplus.entities.RecordDetailEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecordDetailService {
    List<RecordDetailResponseDto> findAll();
    Optional<RecordDetailResponseDto> findById(UUID id);
    List<RecordDetailResponseDto> findByRecordId(UUID recordId);
    RecordDetailResponseDto save(RecordDetailCreateDto recordDetailCreateDto);
    void updateState(UUID id, Integer stateId);
    Optional<RecordDetailEntity> findEntityById(UUID id);
}