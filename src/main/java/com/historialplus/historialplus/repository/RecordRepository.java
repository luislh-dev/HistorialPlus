package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordRepository extends JpaRepository<RecordEntity, UUID> {
}
