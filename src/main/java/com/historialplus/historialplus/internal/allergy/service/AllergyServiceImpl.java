package com.historialplus.historialplus.internal.allergy.service;

import com.historialplus.historialplus.error.exceptions.ConflictException;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.internal.allergy.dto.request.AllergyCreateDto;
import com.historialplus.historialplus.internal.allergy.dto.request.AllergyUpdateDto;
import com.historialplus.historialplus.internal.allergy.dto.response.AllergyResponseDto;
import com.historialplus.historialplus.internal.allergy.entities.AllergyEntity;
import com.historialplus.historialplus.internal.allergy.mapper.AllergyMapper;
import com.historialplus.historialplus.internal.allergy.projection.AllergyDetailsProjection;
import com.historialplus.historialplus.internal.allergy.projection.AllergyPageProjection;
import com.historialplus.historialplus.internal.allergy.repository.AllergyRepository;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService{
    private final AllergyRepository allergyRepository;
    private final AllergyMapper allergyMapper;
    private final PeopleRepository peopleRepository;

    @Override
    @Transactional
    public AllergyResponseDto createAllergy(AllergyCreateDto dto) {
        PeopleEntity people = peopleRepository.findById(dto.getPeopleId())
                .orElseThrow(() -> new NotFoundException(String.format("Persona no encontrada con ID: %s", dto.getPeopleId())));

        if (allergyRepository.existsByPeople_IdAndAllergenSubstanceIgnoreCase(
                dto.getPeopleId(), dto.getAllergenSubstance())) {
            throw new ConflictException(String.format(
                    "La persona ya tiene una alergia registrada para la sustancia '%s'",
                    dto.getAllergenSubstance()
            ));
        }

        AllergyEntity allergy = allergyMapper.toEntity(dto);
        allergy.setPeople(people);
        AllergyEntity savedAllergy = allergyRepository.save(allergy);
        return allergyMapper.toResponseDto(savedAllergy);
    }

    @Override
    @Transactional
    public AllergyResponseDto updateAllergy(Integer allergyId, AllergyUpdateDto dto) {
        AllergyEntity existingAllergy = allergyRepository.findById(allergyId)
                .orElseThrow(() -> new NotFoundException("Alergia no encontrada con ID: " + allergyId));

        allergyMapper.updateEntityFromDto(dto, existingAllergy);
        AllergyEntity updatedAllergy = allergyRepository.save(existingAllergy);

        return allergyMapper.toResponseDto(updatedAllergy);
    }

    @Override
    @Transactional(readOnly = true)
    public AllergyDetailsProjection findAllergyById(Integer allergyId) {
        return allergyRepository.findByAllergyId(allergyId, AllergyDetailsProjection.class)
                .orElseThrow(() -> new NotFoundException("Alergia no encontrada con ID: " + allergyId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyPageProjection> findAllAllergies(Pageable pageable) {
        return allergyRepository.findBy(pageable, AllergyPageProjection.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyPageProjection> findAllergiesByPeopleId(UUID peopleId, Pageable pageable) {
        // Verificación adicional para asegurar que la persona existe antes de realizar la consulta.
        if (!peopleRepository.existsById(peopleId)) {
            throw new NotFoundException("Persona no encontrada con ID: " + peopleId);
        }
        return allergyRepository.findByPeople_Id(peopleId, pageable, AllergyPageProjection.class);
    }

    @Override
    @Transactional
    public void deleteAllergy(Integer allergyId) {
        if (!allergyRepository.existsById(allergyId)) {
            throw new NotFoundException("Alergia no encontrada con ID: " + allergyId);
        }
        allergyRepository.deleteById(allergyId);
    }

    @Override
    @Transactional
    public AllergyResponseDto toggleAllergyStatus(Integer allergyId, Boolean isActive) {
        AllergyEntity existingAllergy = allergyRepository.findById(allergyId)
                .orElseThrow(() -> new NotFoundException("Alergia no encontrada con ID: " + allergyId));

        existingAllergy.setIsActive(isActive);

        AllergyEntity updatedAllergy = allergyRepository.save(existingAllergy);
        return allergyMapper.toResponseDto(updatedAllergy);
    }
}
