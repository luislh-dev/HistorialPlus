package com.historialplus.historialplus.internal.role.repository;

import com.historialplus.historialplus.common.constants.RoleEnum;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
	Optional<RoleEntity> findByName(RoleEnum name);
}
