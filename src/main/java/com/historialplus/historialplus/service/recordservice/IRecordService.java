package com.historialplus.historialplus.service.recordservice;

import com.historialplus.historialplus.dto.recordDTOs.request.RecordCreateDto;
import com.historialplus.historialplus.dto.recordDTOs.response.RecordResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecordService {
    List<RecordResponseDto> findAll();

    Optional<RecordResponseDto> findById(UUID id);

    RecordCreateDto save(RecordCreateDto recordCreateDto);

    UUID findPersonIdByDocumentNumber(String documentNumber);
}
