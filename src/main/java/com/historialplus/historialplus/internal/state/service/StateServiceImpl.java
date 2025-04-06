package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StateServiceImpl implements IStateService{

    private final StateRepository repository;

    @Override
    public Optional<StateEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override public Optional<StateEntity> findByName(StateEnum name) {
        return repository.findByName(name);
    }

    @Override
    public List<StateEntity> findAll() {
        return repository.findAll();
    }
}
