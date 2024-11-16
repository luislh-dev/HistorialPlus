package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;

public interface IPeopleService {
    PeopleResponseDto save(PeopleCreateDto peopleCreateDto);
}
