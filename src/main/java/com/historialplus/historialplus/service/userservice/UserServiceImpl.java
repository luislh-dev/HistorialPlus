package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.mapper.UserDtoMapper;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
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

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}