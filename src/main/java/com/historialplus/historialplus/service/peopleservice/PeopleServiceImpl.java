package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.repository.PeopleRepository;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements IPeopleService {

    private final PeopleRepository repository;

    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.repository = peopleRepository;
    }

    @Override
    public PeopleResponseDto save(PeopleCreateDto peopleCreateDto) {
        var people = repository.save(PeopleDtoMapper.toPeopleEntity(peopleCreateDto));
        return PeopleDtoMapper.toPeopleResponseDto(people);
    }
}
