package com.historialplus.historialplus.internal.recorddetail.repository;

import com.historialplus.historialplus.internal.file.dto.response.FileDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailExtenseResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailProjection;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailWithFilesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RecordDetailRepository extends JpaRepository<RecordDetailEntity, UUID> {
    List<RecordDetailEntity> findByRecordId(UUID recordId);

    @Query("SELECT v FROM RecordDetailEntity v " +
            "LEFT JOIN FETCH v.hospital " +
            "LEFT JOIN FETCH v.doctor " +
            "WHERE v.record.person.id = :personId")
    Set<RecordDetailProjection> findVisitsByPersonId(@Param("personId") UUID personId);

    @Query("SELECT rd FROM RecordDetailEntity rd " +
            "LEFT JOIN FETCH rd.hospital h " +
            "LEFT JOIN FETCH rd.doctor d " +
            "LEFT JOIN FETCH rd.files f " +
            "LEFT JOIN FETCH f.fileType ft " +
            "WHERE rd.record.person.id = :personId " +
            "ORDER BY rd.visitDate DESC")
    Set<RecordDetailWithFilesProjection> findVisitsWithFilesByPersonId(@Param("personId") UUID personId);

    @Query("""
       SELECT new com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailExtenseResponseDto(
           rd.id,
           rd.reason,
           h.name,
           CONCAT(p.name, ' ', p.paternalSurname),
           rd.visitDate,
           NULL
       )
       FROM RecordDetailEntity rd
       JOIN rd.record r
       JOIN r.person pe
       JOIN rd.hospital h
       JOIN rd.doctor d
       JOIN d.person p
       WHERE pe.id = :peopleId
       """)
    Page<RecordDetailExtenseResponseDto> findAllByPeopleId(@Param("peopleId") UUID peopleId, Pageable pageable);

    @Query("""
       SELECT new com.historialplus.historialplus.internal.file.dto.response.FileDetailResponseDto(
           f.name,
           f.sizeInBytes,
           f.url,
           ft.name
       )
       FROM FileEntity f
       JOIN f.fileType ft
       WHERE f.recordDetail.id = :recordDetailId
       """)
    Set<FileDetailResponseDto> findFilesByRecordDetailId(@Param("recordDetailId") UUID recordDetailId);
}