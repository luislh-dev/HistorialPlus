package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.entities.RoleEntity;
import com.historialplus.historialplus.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByName(String username);

    @Override
    @NonNull
    Optional<UserEntity> findById(@NonNull UUID id);

    @Override
    void deleteById(@NonNull UUID id);

    @Query("SELECT DISTINCT u FROM UserEntity u " +
            "LEFT JOIN u.person p " +
            "LEFT JOIN u.hospital h " +
            "WHERE (:username IS NOT NULL AND LOWER(u.name) LIKE LOWER(CONCAT('%', :username, '%'))) OR " +
            "(:dni IS NOT NULL AND LOWER(p.documentNumber) LIKE LOWER(CONCAT('%', :dni, '%'))) OR " +
            "(:hospital IS NOT NULL AND LOWER(h.name) LIKE LOWER(CONCAT('%', :hospital, '%'))) OR " +
            "(:id IS NOT NULL AND u.id = :id) OR " +
            "(:role IS NOT NULL AND :role MEMBER OF u.roleEntities) " +
            "ORDER BY CASE WHEN u.stateEntity.id = 1 THEN 0 ELSE 1 END, u.updatedAt DESC")
    Page<UserEntity> searchUsers(@Param("username") String username,
                                 @Param("dni") String dni,
                                 @Param("hospital") String hospital,
                                 @Param("id") UUID id,
                                 @Param("role") RoleEntity role,
                                 Pageable pageable);

    @Query("SELECT u FROM UserEntity u " +
            "ORDER BY CASE WHEN u.stateEntity.id = 1 THEN 0 ELSE 1 END, u.updatedAt DESC")
    Page<UserEntity> findAllWithCustomOrder(Pageable pageable);
}