package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.util.List;
import java.util.Optional;

public interface IStateService {
    Optional<StateEntity> findById(Integer id);
    List<StateEntity> findAll();
}
