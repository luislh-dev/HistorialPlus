package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.HospitalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer> {
    Page<HospitalEntity> findByNameContainingAndRucContainingAndIdOrStateId(String name, String ruc, Integer id, Integer stateId, Pageable pageable);

    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByRuc(String ruc);
}
