package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.mapper.UserDtoMapper;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.repository.UserRepository;
import com.historialplus.historialplus.service.stateservice.IStateService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final IStateService stateService;

    public UserServiceImpl(UserRepository userRepository, IStateService stateService) {
        this.repository = userRepository;
        this.stateService = stateService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDto> findById(@NonNull UUID id) {
        return repository.findById(id)
                .map(UserDtoMapper::toResponseDto);
    }

    @Override
    @Transactional
    public UserDto save(UserCreateDto userDto) {
        return UserDtoMapper.toDto(repository.save(UserDtoMapper.toEntity(userDto)));
    }

    /**
     * Elimina un usuario por su ID
     * El estado del usuario se cambia a eliminado (ID: 3)
     * @param id ID del usuario a eliminar
     */
    @Override
    @Transactional
    public void deleteById(UUID id) {
        // validar si el estado existe
        stateService.findById(3).flatMap(state -> repository.findById(id)).ifPresent(user -> {
            StateEntity stateEntity = stateService.findById(2).orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
            user.setStateEntity(stateEntity);
            repository.save(user);
        });
    }
}