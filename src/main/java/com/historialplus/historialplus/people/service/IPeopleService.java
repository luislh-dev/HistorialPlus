package com.historialplus.historialplus.people.service;

import com.historialplus.historialplus.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.people.entities.PeopleEntity;

import java.util.Optional;

public interface IPeopleService {
    PeopleResponseDto save(PeopleCreateDto peopleCreateDto);
    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);
    Optional<MinimalPeopleResponseDto> getPersonName(String dni);
    Optional<MinimalPeopleResponseDto> getPersonNameByDocument(Integer id, String documentNumber);
}
