package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer>, JpaSpecificationExecutor<HospitalEntity> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByRuc(String ruc);
}
