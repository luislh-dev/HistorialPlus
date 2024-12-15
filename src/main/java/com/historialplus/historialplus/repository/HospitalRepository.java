package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.HospitalEntity;
import com.historialplus.historialplus.hospital.projection.HospitalNameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer>, JpaSpecificationExecutor<HospitalEntity> {
    Page<HospitalNameProjection> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByRuc(String ruc);
}
