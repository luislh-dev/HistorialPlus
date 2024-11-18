package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, Integer> {
    Optional<PeopleEntity> findByDocumentNumber(String documentNumber);
}
