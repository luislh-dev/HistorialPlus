package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.RecordDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordDetailRepository extends JpaRepository<RecordDetailEntity, UUID> {
}
