package com.historialplus.historialplus.internal.people.service;

import com.historialplus.historialplus.common.enums.DocumentTypeEnum;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.external.ce.mapper.CeMapper;
import com.historialplus.historialplus.external.ce.service.CeService;
import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import com.historialplus.historialplus.external.dni.reniec.mapper.ReniecMapper;
import com.historialplus.historialplus.external.dni.reniec.service.ReniecService;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PersonBasicDTO;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.internal.people.presenters.PeopleRecordPresenter;
import com.historialplus.historialplus.internal.people.projection.PeopleRecordProjection;
import com.historialplus.historialplus.internal.people.repository.PeopleRepository;
import com.historialplus.historialplus.internal.record.service.RecordService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.historialplus.historialplus.common.enums.DocumentTypeEnum.CE;
import static com.historialplus.historialplus.common.enums.DocumentTypeEnum.DNI;

@Service
@AllArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private final ReniecService reniecService;
    private final CeService ceService;
    private final PeopleRepository repository;
    private final RecordService recordService;

    @Override
    @Transactional
    public PeopleResponseDto save(PeopleCreateDto peopleCreateDto) {
        var people = repository.save(PeopleDtoMapper.toPeopleEntity(peopleCreateDto));

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
        return reniecResponse.map(ReniecMapper::toMinimalPeopleDto);

    }

    @Override
    public Optional<MinimalPeopleResponseDto> getPersonNameByDocument(DocumentTypeEnum documentType, String documentNumber) {

        if (documentType == null) { return Optional.empty(); }

        Optional<PeopleEntity> people = repository.findByDocumentNumberAndTypeDocument_Id(documentNumber, documentType);
        if (people.isPresent()) {
            LOGGER.info("getPersonNameByDocument internal system");
            return Optional.of(PeopleDtoMapper.toMinimalPeopleDto(people.get()));
        }

        if (documentType.equals(DNI)) {
            try {
                LOGGER.info("getPersonNameByDocument reniecService");
                Optional<ReniecResponseDto> reniecResponse = reniecService.getPersonData(documentNumber);
                return reniecResponse.map(ReniecMapper::toMinimalPeopleDto);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        if (documentType.equals(CE)) {

            if (documentNumber.length() < 9) {
                return Optional.empty();
            }

            try {
                LOGGER.info("getPersonNameByDocument ceService");
                Optional<CeResponseDto> ceResponse = ceService.getCeData(documentNumber);
                if (ceResponse.isEmpty()) {
                    return Optional.empty();
                }

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
    public Optional<PersonBasicDTO> findBasicById(UUID id) {
        return repository.findBasicById(id).map(PeopleDtoMapper::toPersonBasicDto);
    }
}
