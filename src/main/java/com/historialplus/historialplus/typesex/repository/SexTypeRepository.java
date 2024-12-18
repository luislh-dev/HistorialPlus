package com.historialplus.historialplus.typesex.repository;

import com.historialplus.historialplus.typesex.entities.SexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexTypeRepository extends JpaRepository<SexTypeEntity, Integer> {
}
