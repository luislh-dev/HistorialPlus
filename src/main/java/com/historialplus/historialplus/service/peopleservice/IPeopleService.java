package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;

public interface IPeopleService {
    PeopleCreateDto save(PeopleCreateDto peopleCreateDto);
}
