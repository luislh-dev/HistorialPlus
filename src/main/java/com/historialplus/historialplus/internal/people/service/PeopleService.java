package com.historialplus.historialplus.internal.people.service;

import com.historialplus.historialplus.common.enums.DocumentTypeEnum;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PersonBasicDTO;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.presenters.PeopleRecordPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PeopleService {
    PeopleResponseDto save(PeopleCreateDto peopleCreateDto);

    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);

    Optional<MinimalPeopleResponseDto> getPersonName(String dni);

    Optional<MinimalPeopleResponseDto> getPersonNameByDocument(DocumentTypeEnum documentType, String documentNumber);

    Page<PeopleRecordPresenter> findAllWithVisitsStats(String documentNumber, String fullName, Pageable pageable);

    Optional<PersonBasicDTO> findBasicById(UUID id);
}
