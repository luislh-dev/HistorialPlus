package com.historialplus.historialplus.state.repository;

import com.historialplus.historialplus.state.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {

}
