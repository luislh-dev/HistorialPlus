package com.historialplus.historialplus.service.stateservice;

import com.historialplus.historialplus.model.StateModel;

import java.util.Optional;

public interface IStateService {
    Optional<StateModel> findById(Integer id);
}
