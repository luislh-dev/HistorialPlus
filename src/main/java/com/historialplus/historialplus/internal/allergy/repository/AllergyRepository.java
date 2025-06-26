package com.historialplus.historialplus.internal.allergy.repository;

import com.historialplus.historialplus.internal.allergy.entities.AllergyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyEntity, Integer> {
    <T> Optional<T> findByAllergyId(Integer allergyId, Class<T> type); // Para proyecciones
    <T> Page<T> findByPeople_Id(UUID peopleId, Pageable pageable, Class<T> type);
    <T> Page<T> findBy(Pageable pageable, Class<T> type);
    boolean existsByPeople_IdAndAllergenSubstanceIgnoreCase(UUID peopleId, String allergenSubstance);
}
