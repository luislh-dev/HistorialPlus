package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.util.List;
import java.util.Optional;

public interface IStateService {
    Optional<StateEntity> findById(Integer id);
    Optional<StateEntity> findByName(StateEnum name);
    List<StateEntity> findAll();
}
