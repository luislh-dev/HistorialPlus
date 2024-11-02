package com.historialplus.historialplus.service.stateservice;

import com.historialplus.historialplus.entities.StateEntity;

import java.util.Optional;

public interface IStateService {
    Optional<StateEntity> findById(Integer id);
}
