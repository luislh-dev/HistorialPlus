package com.historialplus.historialplus.service.stateservice;

import com.historialplus.historialplus.model.StateModel;
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
    public Optional<StateModel> findById(Integer id) {
        return repository.findById(id);
    }
}
