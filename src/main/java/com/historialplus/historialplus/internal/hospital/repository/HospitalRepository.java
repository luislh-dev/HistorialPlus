package com.historialplus.historialplus.internal.hospital.repository;

import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.hospital.projection.HospitalDetailsProjection;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import com.historialplus.historialplus.internal.hospital.projection.HospitalPageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer> {
    Page<HospitalNameProjection> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByRuc(String ruc);

    @Query("""
        SELECT h.id AS id, h.name AS name, h.ruc AS ruc, h.email AS email, h.phone AS phone, s.name AS state
        FROM HospitalEntity h
        JOIN h.state s
        WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:ruc IS NULL OR LOWER(h.ruc) LIKE LOWER(CONCAT('%', :ruc, '%')))
        AND (:id IS NULL OR h.id = :id)
        AND (:stateId IS NULL OR s.id = :stateId)
        GROUP BY h.id, h.name, h.ruc, h.email, h.phone, s.name
    """)
    Page<HospitalPageProjection> findAllWithProjection(
        @Param("name") String name,
        @Param("ruc") String ruc,
        @Param("id") Integer id,
        @Param("stateId") Integer stateId,
        Pageable pageable);

    @Query("SELECT h.name AS name, h.address AS address, h.phone AS phone, h.email AS email, h.ruc AS ruc, h.state.id AS stateId " +
           "FROM HospitalEntity h " +
           "WHERE h.id = :id")
    Optional<HospitalDetailsProjection> findProjectedById(Integer id);
}
