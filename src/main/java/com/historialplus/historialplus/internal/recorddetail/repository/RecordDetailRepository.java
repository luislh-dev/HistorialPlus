package com.historialplus.historialplus.internal.recorddetail.repository;

import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RecordDetailRepository extends JpaRepository<RecordDetailEntity, UUID> {
    List<RecordDetailEntity> findByRecordId(UUID recordId);

    @Query("""
                SELECT rd FROM RecordDetailEntity rd
                LEFT JOIN FETCH rd.files f
                LEFT JOIN FETCH f.fileType
                LEFT JOIN FETCH rd.hospital
                LEFT JOIN FETCH rd.doctor d
                LEFT JOIN FETCH d.person p
                LEFT JOIN FETCH p.sexType
                WHERE rd.record.person.id = :peopleId
                AND (:hospitalName IS NULL OR rd.hospital.name LIKE CONCAT('%', :hospitalName, '%'))
                AND (:startDate IS NULL OR rd.visitDate >= :startDate)
                AND (:endDate IS NULL OR rd.visitDate <= :endDate)
                ORDER BY rd.visitDate DESC
            """)
    Page<RecordDetailProjection> findProjectedByRecord_Person_Id(
            @Param("peopleId") UUID peopleId,
            @Param("hospitalName") String hospitalName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}