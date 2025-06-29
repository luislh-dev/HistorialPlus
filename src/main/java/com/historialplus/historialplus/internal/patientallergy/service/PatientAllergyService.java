package com.historialplus.historialplus.internal.patientallergy.service;

import com.historialplus.historialplus.internal.patientallergy.dto.request.AssignPatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.request.UpdatePatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.response.PatientAllergyResponseDto;

import java.util.List;
import java.util.UUID;

public interface PatientAllergyService {
    PatientAllergyResponseDto assignToRecord(AssignPatientAllergyDto dto);
    List<PatientAllergyResponseDto> findByRecordId(UUID recordId);
    PatientAllergyResponseDto update(UUID assignedAllergyId, UpdatePatientAllergyDto dto);
    PatientAllergyResponseDto deactivate(UUID assignedAllergyId);
    PatientAllergyResponseDto reactivate(UUID assignedAllergyId);
}
