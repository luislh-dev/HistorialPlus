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

    @Query(value = """
        SELECT rd.id as id, rd.reason as reason, rd.visitDate as visitDate,
               h.name as hospitalName,
               CONCAT(p.name, ' ', p.paternalSurname) as doctorFullName,
               p.sexType.id as sexTypeId
        FROM RecordDetailEntity rd
        JOIN HospitalEntity h ON rd.hospital.id = h.id
        JOIN UserEntity u ON rd.doctor.id = u.id
        JOIN PeopleEntity p ON u.person.id = p.id
        WHERE rd.record.id IN (
            SELECT r.id FROM RecordEntity r WHERE r.person.id = :peopleId
        )
        AND (:hospitalName IS NULL OR h.name LIKE CONCAT('%', :hospitalName, '%'))
        AND (:startDate IS NULL OR rd.visitDate >= :startDate)
        AND (:endDate IS NULL OR rd.visitDate <= :endDate)
        ORDER BY rd.visitDate DESC
    """)
    Page<RecordDetailProjection> findBasicDetailsByPersonId(
            @Param("peopleId") UUID peopleId,
            @Param("hospitalName") String hospitalName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}