package com.historialplus.historialplus.record.repository;

import com.historialplus.historialplus.record.entites.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {
    boolean existsByPersonId(UUID personId);
}