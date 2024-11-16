package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.SexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexTypeRepository extends JpaRepository<SexTypeEntity, Integer> {
}
