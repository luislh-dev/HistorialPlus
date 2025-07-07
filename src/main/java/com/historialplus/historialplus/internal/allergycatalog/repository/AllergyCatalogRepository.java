package com.historialplus.historialplus.internal.allergycatalog.repository;

import com.historialplus.historialplus.internal.allergycatalog.entities.AllergyCatalogEntity;
import com.historialplus.historialplus.internal.allergycatalog.projection.AllergyCatalogProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergyCatalogRepository extends JpaRepository<AllergyCatalogEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

    Optional<AllergyCatalogEntity> findFirstByOrderByCreatedAtDesc();

    Page<AllergyCatalogEntity> findAllByIsActiveTrue(Pageable pageable);

    @Query("""
        SELECT a.id AS id, a.code AS code, a.name AS name, a.category AS category, a.isActive AS isActive FROM AllergyCatalogEntity a
        WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:isActive IS NULL OR a.isActive = :isActive)
    """)
    Page<AllergyCatalogProjection> findAllWithProjection(String name, Boolean isActive, Pageable pageable);
}
