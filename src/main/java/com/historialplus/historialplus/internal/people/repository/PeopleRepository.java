package com.historialplus.historialplus.internal.people.repository;

import com.historialplus.historialplus.common.constants.DocumentTypeEnum;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.people.projection.PeopleRecordProjection;
import com.historialplus.historialplus.internal.people.projection.PersonaBasicProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, UUID>, JpaSpecificationExecutor<PeopleEntity> {
    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);

    Optional<PeopleEntity> findByDocumentNumberAndTypeDocument_Name(String documentNumber, DocumentTypeEnum typeDocumentName);

    @Query("""
    SELECT new com.historialplus.historialplus.internal.people.projection.PeopleRecordProjection(
        p.id,
        p.documentNumber,
        CONCAT(p.name, ' ', p.paternalSurname, ' ', p.maternalSurname),
        CAST(COUNT(rd.id) AS int),
        MAX(rd.visitDate),
        MAX(h.name)
    )
    FROM PeopleEntity p
    LEFT JOIN p.record r
    LEFT JOIN r.visits rd
    LEFT JOIN rd.hospital h
    WHERE (:documentNumber IS NULL OR p.documentNumber = :documentNumber)
    AND (:fullName IS NULL OR LOWER(CONCAT(p.name, ' ', p.paternalSurname, ' ', p.maternalSurname)) LIKE LOWER(CONCAT('%', :fullName, '%')))
    GROUP BY p.id, p.documentNumber, p.name, p.paternalSurname, p.maternalSurname, p.createdAt, p.updatedAt
    """)
    Page<PeopleRecordProjection> findAllWithVisitsStats(
            @Param("documentNumber") String documentNumber,
            @Param("fullName") String fullName,
            Pageable pageable
    );

    @Query("SELECT p FROM PeopleEntity p " +
            "LEFT JOIN FETCH p.sexType " +
            "LEFT JOIN FETCH p.typeDocument " +
            "WHERE p.id = :id")
    Optional<PersonaBasicProjection> findBasicById(@Param("id") UUID id);
}
