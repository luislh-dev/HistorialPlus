package com.historialplus.historialplus.repository;

import com.historialplus.historialplus.model.StateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateModel, Integer> {

}
