package com.historialplus.historialplus.service.recordservice;

import com.historialplus.historialplus.dto.recordDTOs.mapper.RecordDtoMapper;
import com.historialplus.historialplus.dto.recordDTOs.request.RecordCreateDto;
import com.historialplus.historialplus.dto.recordDTOs.response.RecordResponseDto;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.entities.RecordEntity;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.PeopleRepository;
import com.historialplus.historialplus.repository.RecordRepository;
import com.historialplus.historialplus.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements IRecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;

    public RecordServiceImpl(RecordRepository recordRepository, UserRepository userRepository, PeopleRepository peopleRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
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

    @Transactional
    @Override
    public RecordCreateDto save(RecordCreateDto recordCreateDto) {
        // Get the hospital ID from the current user's session
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // a partir del nombre del usuario logeado recuperame su hospital id
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        // creame un entidad de record
        RecordEntity recordEntity = new RecordEntity();

        // setea el hospital de la entidad record con el hospital del usuario
        recordEntity.setHospital(user.getHospital());

        // encontrar el id de la persona por el numero de documento
        UUID personId = findPersonIdByDocumentNumber(recordCreateDto.getDocumentNumber());

        recordEntity.setPerson(peopleRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + personId)));

        recordRepository.save(recordEntity);
        Logger.getLogger("RecordServiceImpl").info("Record saved successfully");
        return recordCreateDto;
    }

    @Override
    public UUID findPersonIdByDocumentNumber(String documentNumber) {
        PeopleEntity person = peopleRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with document number: " + documentNumber));
        return person.getId();
    }
}