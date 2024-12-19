package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.dto.reniecDTO.ReniecResponseDto;
import com.historialplus.historialplus.dto.reniecDTO.mapper.reniecMapper;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.repository.PeopleRepository;
import com.historialplus.historialplus.service.reniecservice.IReniecService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PeopleServiceImpl implements IPeopleService {

    private final IReniecService reniecService;
    private final PeopleRepository repository;

    public PeopleServiceImpl(PeopleRepository peopleRepository, IReniecService reniecService) {
        this.repository = peopleRepository;
        this.reniecService = reniecService;
    }

    @Override
    @Transactional
    public PeopleResponseDto save(PeopleCreateDto peopleCreateDto) {
        var people = repository.save(PeopleDtoMapper.toPeopleEntity(peopleCreateDto));
        return PeopleDtoMapper.toPeopleResponseDto(people);
    }

    @Override
    public Optional<PeopleEntity> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Optional<MinimalPeopleResponseDto> getPersonName(String dni) {
        Optional<PeopleEntity> people = findByDocumentNumber(dni);
        if (people.isPresent()) {
            return Optional.of(PeopleDtoMapper.toMinimalPeopleDto(people.get()));
        }

        Optional<ReniecResponseDto> reniecResponse = reniecService.getPersonData(dni);
        return reniecResponse.map(reniecMapper::toMinimalPeopleDto);

    }

    @Override
    public Optional<MinimalPeopleResponseDto> getPersonNameByDocument(Integer id, String documentNumber) {
        // validar

        return Optional.empty();
    }
}
