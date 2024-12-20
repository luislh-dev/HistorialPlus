package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.entities.PeopleEntity;

import java.util.Optional;

public interface IPeopleService {
    PeopleResponseDto save(PeopleCreateDto peopleCreateDto);
    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);
    Optional<MinimalPeopleResponseDto> getPersonName(String dni);
    Optional<MinimalPeopleResponseDto> getPersonNameByDocument(Integer id, String documentNumber);
}
