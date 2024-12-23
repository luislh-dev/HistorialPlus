package com.historialplus.historialplus.internal.typesex.repository;

import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexTypeRepository extends JpaRepository<SexTypeEntity, Integer> {
}
