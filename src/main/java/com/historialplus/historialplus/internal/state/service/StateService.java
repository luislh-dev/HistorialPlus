package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.common.enums.StateEnum;
import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.util.List;
import java.util.Optional;

public interface StateService {
    Optional<StateEntity> findById(Integer id);
    Optional<StateEntity> findByName(StateEnum name);
    List<StateDto> findAll();
}
