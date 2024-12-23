package com.historialplus.historialplus.internal.people.repository;

import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, UUID> {
    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);
    Optional<PeopleEntity> findByDocumentNumberAndTypeDocument_Id(String documentNumber, Integer typeDocumentId);

}
