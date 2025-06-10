package com.historialplus.historialplus.internal.people.service;

import com.historialplus.historialplus.common.constants.DocumentTypeEnum;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.external.ce.mapper.CeMapper;
import com.historialplus.historialplus.external.ce.service.ICeService;
import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import com.historialplus.historialplus.external.dni.reniec.mapper.reniecMapper;
import com.historialplus.historialplus.external.dni.reniec.service.IReniecService;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.internal.people.presenters.PeopleRecordPresenter;
import com.historialplus.historialplus.internal.people.projection.PeopleRecordProjection;
import com.historialplus.historialplus.internal.people.projection.PersonaBasicProjection;
import com.historialplus.historialplus.internal.people.repository.PeopleRepository;
import com.historialplus.historialplus.internal.record.service.IRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.CE;
import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.DNI;

@Service
@AllArgsConstructor
public class PeopleServiceImpl implements IPeopleService {

    private final IReniecService reniecService;
    private final ICeService ceService;
    private final PeopleRepository repository;
    private final IRecordService recordService;

    @Override
    @Transactional
    public PeopleResponseDto save(PeopleCreateDto peopleCreateDto) {
        var people = repository.save(PeopleDtoMapper.toPeopleEntity(peopleCreateDto));

        // Guardar la persona en la tabla de records
        recordService.save(people);

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
    public Optional<MinimalPeopleResponseDto> getPersonNameByDocument(String documentType, String documentNumber) {

        DocumentTypeEnum documentTypeEnum = DocumentTypeEnum.getDocumentTypeByName(documentType);

        if (documentTypeEnum == null) { return Optional.empty(); }

        // verificamos si la persona ya existe en la base de datos
        Optional<PeopleEntity> people = repository.findByDocumentNumberAndTypeDocument_Name(documentNumber, documentTypeEnum);
        if (people.isPresent()) {
            return Optional.of(PeopleDtoMapper.toMinimalPeopleDto(people.get()));
        }

        // si no existe, consultamos a reniec
        if (documentTypeEnum.equals(DNI)) {
            try {
                Optional<ReniecResponseDto> reniecResponse = reniecService.getPersonData(documentNumber);
                return reniecResponse.map(reniecMapper::toMinimalPeopleDto);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        // Validamos si el tipo es Carnet de Extranjería
        if (documentTypeEnum.equals(CE)) {

            if (documentNumber.length() < 9) {
                return Optional.empty();
            }

            try {
                Optional<CeResponseDto> ceResponse = ceService.getCeData(documentNumber);
                return ceResponse.map(CeMapper::toMinimalPeopleDto);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Override
    public Page<PeopleRecordPresenter> findAllWithVisitsStats(String documentNumber, String fullName, Pageable pageable) {
        Page<PeopleRecordProjection> projectionPage = repository.findAllWithVisitsStats(
                documentNumber != null && !documentNumber.trim().isEmpty() ? documentNumber.trim() : null,
                fullName != null && !fullName.trim().isEmpty() ? fullName.trim() : null,
                pageable
        );

        return projectionPage.map(PeopleRecordPresenter::fromProjection);
    }

    @Override
    public Optional<PersonaBasicProjection> findBasicById(UUID id) {
        return repository.findBasicById(id);
    }
}
