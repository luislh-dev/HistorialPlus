package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByName(String username);

    @Override
    @NonNull
    Optional<UserModel> findById(@NonNull UUID id);

    @Override
    void deleteById(@NonNull UUID id);
}