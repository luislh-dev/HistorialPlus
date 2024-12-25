package com.historialplus.historialplus.internal.record.service;

import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.record.dto.request.RecordCreateDto;
import com.historialplus.historialplus.internal.record.dto.response.RecordResponseDto;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecordService {
    List<RecordResponseDto> findAll();

    Optional<RecordResponseDto> findById(UUID id);

    RecordCreateDto save(RecordCreateDto recordCreateDto);

    RecordEntity save(PeopleEntity people);

    UUID findPersonIdByDocumentNumber(String documentNumber);
}
