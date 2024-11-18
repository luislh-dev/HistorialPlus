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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public RecordResponseDto save(RecordCreateDto recordCreateDto) {
        // Get the hospital ID from the current user's session
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Fetch the user by username and retrieve the hospital ID
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        Integer hospitalId = user.getHospitalId();
        if (hospitalId == null) {
            throw new IllegalArgumentException("Hospital ID must not be null");
        }

        // Fetch the person by document number
        PeopleEntity person = peopleRepository.findByDocumentNumber(recordCreateDto.getDocumentNumber())
                .orElseThrow(() -> new IllegalArgumentException("Person not found with document number: " + recordCreateDto.getDocumentNumber()));

        // Create the record entity
        RecordEntity recordEntity = RecordDtoMapper.toEntity(recordCreateDto, person, user.getHospital());

        RecordEntity savedRecord = recordRepository.save(recordEntity);
        return RecordDtoMapper.toResponseDto(savedRecord);
    }

    @Override
    public UUID findPersonIdByDocumentNumber(String documentNumber) {
        PeopleEntity person = peopleRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with document number: " + documentNumber));
        return person.getId();
    }
}