package com.historialplus.historialplus.internal.user.repository;

import com.historialplus.historialplus.internal.user.entites.UserEntity;
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

    @Override
    void deleteById(@NonNull UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByPersonIdAndHospitalId(UUID personId, Integer hospitalId);

    @Query("SELECT u FROM UserEntity u " +
           "WHERE (:name IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:dni IS NULL OR u.person.documentNumber = :dni) " +
           "AND (:hospitalName IS NULL OR LOWER(u.hospital.name) LIKE LOWER(CONCAT('%', :hospitalName, '%'))) " +
           "AND (:roleId IS NULL OR EXISTS (SELECT r FROM u.roleEntities r WHERE r.id = :roleId)) " +
           "AND (:stateId IS NULL OR u.state.id = :stateId)")
    Page<UserEntity> findByFilters(
        @Param("name") String name,
        @Param("dni") String dni,
        @Param("hospitalName") String hospitalName,
        @Param("roleId") Integer roleId,
        @Param("stateId") Integer stateId,
        Pageable pageable);
}