package com.historialplus.historialplus.internal.allergycatalog.repository;

import com.historialplus.historialplus.internal.allergycatalog.entities.AllergyCatalogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergyCatalogRepository extends JpaRepository<AllergyCatalogEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

    Optional<AllergyCatalogEntity> findFirstByOrderByCreatedAtDesc();

    Page<AllergyCatalogEntity> findAllByIsActiveTrue(Pageable pageable);
}
