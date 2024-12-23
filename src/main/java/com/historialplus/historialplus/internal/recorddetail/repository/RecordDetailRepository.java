package com.historialplus.historialplus.internal.recorddetail.repository;

import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordDetailRepository extends JpaRepository<RecordDetailEntity, UUID> {
    List<RecordDetailEntity> findByRecordId(UUID recordId);
}