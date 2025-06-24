package com.historialplus.historialplus.internal.sextype.repository;

import com.historialplus.historialplus.internal.sextype.entities.SexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexTypeRepository extends JpaRepository<SexTypeEntity, Integer> {
}
