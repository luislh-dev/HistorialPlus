package com.historialplus.historialplus.internal.record.service;

import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.repository.PeopleRepository;
import com.historialplus.historialplus.internal.record.dto.response.RecordResponseDto;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.record.mapper.RecordDtoMapper;
import com.historialplus.historialplus.internal.record.repository.RecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements IRecordService {
    private final RecordRepository recordRepository;
    private final PeopleRepository peopleRepository;

    public RecordServiceImpl(RecordRepository recordRepository, PeopleRepository peopleRepository) {
        this.recordRepository = recordRepository;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<RecordResponseDto> findAll() {
        return recordRepository.findAll().stream()
                .map(RecordDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecordResponseDto> findById(UUID id) {
        return recordRepository.findById(id)
                .map(RecordDtoMapper::toResponseDto);
    }


    @Override
    @Transactional
    public RecordEntity save(PeopleEntity people) {
        // Carer un nuevo registro
        RecordEntity entity = new RecordEntity();
        entity.setPerson(people);

        return recordRepository.save(entity);
    }

    @Override
    public UUID findPersonIdByDocumentNumber(String documentNumber) {
        PeopleEntity person = peopleRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with document number: " + documentNumber));
        return person.getId();
    }

}