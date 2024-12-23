package com.historialplus.historialplus.user.repository;

import com.historialplus.historialplus.user.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
}