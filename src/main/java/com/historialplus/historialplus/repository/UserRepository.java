package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByName(String username);

    @Override
    @NonNull
    Optional<User> findById(@NonNull UUID id);

    @Override
    void deleteById(@NonNull UUID id);
}