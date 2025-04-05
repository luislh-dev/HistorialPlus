package com.historialplus.historialplus.internal.user.repository;

import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.projection.UserListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);

    @Override
    @NonNull
    Optional<UserEntity> findById(@NonNull UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByPersonIdAndHospitalId(UUID personId, Integer hospitalId);

    @Query("SELECT u.id AS id, u.username AS username, u.email AS email, " +
           "p.documentNumber AS dni, h.name AS hospital, s.name AS state, " +
           "GROUP_CONCAT(r.name) AS roles " +
           "FROM UserEntity u " +
           "JOIN u.person p " +
           "JOIN u.hospital h " +
           "JOIN u.state s " +
           "JOIN u.roleEntities r " +
           "WHERE (:name IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:dni IS NULL OR p.documentNumber = :dni) " +
           "AND (:hospitalName IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :hospitalName, '%'))) " +
           "AND (:roleId IS NULL OR r.id = :roleId) " +
           "AND (:stateId IS NULL OR s.id = :stateId) " +
           "GROUP BY u.id, u.username, u.email, p.documentNumber, h.name, s.name")
    Page<UserListProjection> findByFilters(
        @Param("name") String name,
        @Param("dni") String dni,
        @Param("hospitalName") String hospitalName,
        @Param("roleId") Integer roleId,
        @Param("stateId") Integer stateId,
        Pageable pageable);
    
}