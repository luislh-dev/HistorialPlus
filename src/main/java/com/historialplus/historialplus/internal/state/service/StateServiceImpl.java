package com.historialplus.historialplus.internal.state.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.state.dto.StateDto;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.mapper.StateMapper;
import com.historialplus.historialplus.internal.state.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository repository;
    private final StateMapper mapper;

    @Override
    public Optional<StateEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override public Optional<StateEntity> findByName(StateEnum name) {
        return repository.findByName(name);
    }

    @Override
    public List<StateDto> findAll() {
        return repository.findAll().stream().map(mapper::stateEntityToStateDto).collect(Collectors.toList());
    }
}
