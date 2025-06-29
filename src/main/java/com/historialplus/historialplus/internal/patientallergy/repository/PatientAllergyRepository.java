package com.historialplus.historialplus.internal.patientallergy.repository;

import com.historialplus.historialplus.internal.patientallergy.entities.PatientAllergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientAllergyRepository extends JpaRepository<PatientAllergyEntity, UUID> {

    List<PatientAllergyEntity> findByMedicalRecord_IdAndIsActiveTrueOrderByAllergyName(UUID recordId);

    boolean existsByMedicalRecord_IdAndAllergy_Id(UUID recordId, UUID allergyId);
}