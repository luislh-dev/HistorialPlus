package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements IPeopleService {

    @Autowired
    private final PeopleRepository repository;

    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.repository = peopleRepository;
    }

    @Override
    public PeopleResponseDto save(PeopleCreateDto peopleCreateDto) {
        var people = repository.save(PeopleDtoMapper.toPeopleEntity(peopleCreateDto));
        return PeopleDtoMapper.toPeopleResponseDto(people);
    }

    public PeopleEntity findByDocumentNumber(String documentNumber) {
        //revisar si es necesario el cast
        return (PeopleEntity) repository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }
}
