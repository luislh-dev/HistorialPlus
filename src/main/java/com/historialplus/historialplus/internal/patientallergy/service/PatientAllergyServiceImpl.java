package com.historialplus.historialplus.internal.patientallergy.service;

import com.historialplus.historialplus.error.exceptions.ConflictException;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.internal.allergycatalog.entities.AllergyCatalogEntity;
import com.historialplus.historialplus.internal.allergycatalog.repository.AllergyCatalogRepository;
import com.historialplus.historialplus.internal.patientallergy.dto.request.AssignPatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.request.UpdatePatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.response.PatientAllergyResponseDto;
import com.historialplus.historialplus.internal.patientallergy.entities.PatientAllergyEntity;
import com.historialplus.historialplus.internal.patientallergy.mapper.PatientAllergyMapper;
import com.historialplus.historialplus.internal.patientallergy.repository.PatientAllergyRepository;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientAllergyServiceImpl implements PatientAllergyService {

    private final PatientAllergyRepository patientAllergyRepository;
    private final RecordRepository recordRepository;
    private final AllergyCatalogRepository allergyCatalogRepository;
    private final PatientAllergyMapper patientAllergyMapper;

    @Override
    @Transactional
    public PatientAllergyResponseDto assignToRecord(AssignPatientAllergyDto dto) {
        RecordEntity medicalRecord = recordRepository.findById(dto.getRecordId())
                .orElseThrow(() -> new NotFoundException("Historial médico no encontrado con ID: " + dto.getRecordId()));

        AllergyCatalogEntity allergy = allergyCatalogRepository.findById(dto.getAllergyCatalogId())
                .orElseThrow(() -> new NotFoundException("Alergia no encontrada en el catálogo con ID: " + dto.getAllergyCatalogId()));

        if (patientAllergyRepository.existsByMedicalRecord_IdAndAllergy_Id(medicalRecord.getId(), allergy.getId())) {
            throw new ConflictException("El paciente ya tiene esta alergia asignada.");
        }

        PatientAllergyEntity newAssignedAllergy = PatientAllergyEntity.builder()
                .medicalRecord(medicalRecord)
                .allergy(allergy)
                .severity(dto.getSeverity())
                .reactionDescription(dto.getReactionDescription())
                .diagnosisDate(dto.getDiagnosisDate())
                .build();

        PatientAllergyEntity savedEntity = patientAllergyRepository.save(newAssignedAllergy);
        return patientAllergyMapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientAllergyResponseDto> findByRecordId(UUID recordId) {
        List<PatientAllergyEntity> allergies = patientAllergyRepository.findByMedicalRecord_IdAndIsActiveTrueOrderByAllergyName(recordId);
        return patientAllergyMapper.toDtoList(allergies);
    }

    @Override
    @Transactional
    public PatientAllergyResponseDto update(UUID assignedAllergyId, UpdatePatientAllergyDto dto) {
        PatientAllergyEntity entity = findEntityById(assignedAllergyId);
        patientAllergyMapper.updateEntityFromDto(dto, entity);
        return patientAllergyMapper.toDto(patientAllergyRepository.save(entity));
    }

    @Override
    @Transactional
    public PatientAllergyResponseDto deactivate(UUID assignedAllergyId) {
        PatientAllergyEntity entity = findEntityById(assignedAllergyId);
        entity.setIsActive(false);
        return patientAllergyMapper.toDto(patientAllergyRepository.save(entity));
    }

    @Override
    @Transactional
    public PatientAllergyResponseDto reactivate(UUID assignedAllergyId) {
        PatientAllergyEntity entity = findEntityById(assignedAllergyId);
        entity.setIsActive(true);
        return patientAllergyMapper.toDto(patientAllergyRepository.save(entity));
    }

    private PatientAllergyEntity findEntityById(UUID id) {
        return patientAllergyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asignación de alergia no encontrada con ID: " + id));
    }
}
