package com.historialplus.historialplus.service.peopleservice;

import com.historialplus.historialplus.dto.peopleDTOs.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.dto.peopleDTOs.response.PeopleResponseDto;
import com.historialplus.historialplus.dto.reniecDTO.ReniecResponseDto;
import com.historialplus.historialplus.dto.reniecDTO.mapper.reniecMapper;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
import com.historialplus.historialplus.external.ce.mapper.CeMapper;
import com.historialplus.historialplus.external.ce.service.ICeService;
import com.historialplus.historialplus.repository.PeopleRepository;
import com.historialplus.historialplus.service.reniecservice.IReniecService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.historialplus.historialplus.common.constants.DocumentTypeConstants.CE_ID;
import static com.historialplus.historialplus.common.constants.DocumentTypeConstants.DNI_ID;

@Service
public class PeopleServiceImpl implements IPeopleService {

    private final IReniecService reniecService;
    private final ICeService ceService;
    private final PeopleRepository repository;

    public PeopleServiceImpl(PeopleRepository peopleRepository, IReniecService reniecService, ICeService ceService) {
        this.repository = peopleRepository;
        this.reniecService = reniecService;
        this.ceService = ceService;
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
        // verificamos si la persona ya existe en la base de datos
        Optional<PeopleEntity> people = findByDocumentNumber(documentNumber);
        if (people.isPresent()) {
            return Optional.of(PeopleDtoMapper.toMinimalPeopleDto(people.get()));
        }

        // si no existe, consultamos a reniec
        if(id.equals(DNI_ID)){
            try {
                Optional<ReniecResponseDto> reniecResponse = reniecService.getPersonData(documentNumber);
                return reniecResponse.map(reniecMapper::toMinimalPeopleDto);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        // Validamos si el tipo es Carnet de Extranjería
        if (id.equals(CE_ID)){

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

        // Si no es ninguno de los dos, retornamos un optional vacío
        return Optional.empty();
    }
}
