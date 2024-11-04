package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.UserDto;
import com.historialplus.historialplus.dto.mapper.UserDtoMapper;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(@NonNull UUID id) {
        return repository.findById(id)
                .map(UserDtoMapper::toDto);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = UserDtoMapper.toEntity(userDto);
        return UserDtoMapper.toDto(repository.save(userEntity));
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}