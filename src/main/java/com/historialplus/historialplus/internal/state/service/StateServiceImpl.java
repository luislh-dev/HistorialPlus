package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements IStateService{

    private final StateRepository repository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.repository = stateRepository;
    }

    @Override
    public Optional<StateEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<StateEntity> findAll() {
        return repository.findAll();
    }
}
