package com.historialplus.historialplus.service.stateservice;

import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.repository.StateRepository;
import org.springframework.stereotype.Service;

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
}
