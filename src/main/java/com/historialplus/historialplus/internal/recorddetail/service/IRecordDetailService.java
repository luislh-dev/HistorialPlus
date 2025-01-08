package com.historialplus.historialplus.internal.recorddetail.service;

import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateRequestDTO;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.presenters.RecordDetailPresenter;
import com.historialplus.historialplus.internal.recorddetail.viewmodel.RecordDetailExtenseViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IRecordDetailService {
    List<RecordDetailResponseDto> findAll();

    Optional<RecordDetailResponseDto> findById(UUID id);

    List<RecordDetailResponseDto> findByRecordId(UUID recordId);

    void updateState(UUID id, Integer stateId);

    Optional<RecordDetailEntity> findEntityById(UUID id);

    CompletableFuture<RecordDetailResponseDto> save(RecordDetailCreateRequestDTO dto);

    Page<RecordDetailPresenter> getRecordDetails(UUID recordId, String hospitalName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    RecordDetailExtenseViewModel getRecordDetail(UUID recordDetailId);
}