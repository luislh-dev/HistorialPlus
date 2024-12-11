package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.mapper.UserDtoMapper;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserUpdateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserListResponseDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.RoleEntity;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
import com.historialplus.historialplus.service.stateservice.IStateService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.constants.RoleConstants.ROLE_MANAGEMENT;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final IStateService stateService;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, IStateService stateService, UserDtoMapper userDtoMapper) {
        this.repository = userRepository;
        this.stateService = stateService;
        this.userDtoMapper = userDtoMapper;
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
        return UserDtoMapper.toDto(repository.save( userDtoMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public UserResponseDto createHospitalUserByManagement(UserCreateDto userDto) {
        // Obtener el usuario de gestión autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity managementUser = repository.findByName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario de gestión no encontrado"));

        if (managementUser.getRoleEntities().stream().noneMatch(role -> role.getName().equals(ROLE_MANAGEMENT))) {
            throw new IllegalArgumentException("El usuario no tiene permisos de gestión");
        }

        UserEntity newUser = userDtoMapper.toEntity(userDto);
        newUser.setHospital(managementUser.getHospital()); // Asignar el hospital del managementUser al nuevo usuario

        return UserDtoMapper.toResponseDto(repository.save(newUser));
    }

    @Override
    @Transactional
    public UserResponseDto update(UUID id, UserUpdateDto userDto) {
        // actualizacion parcial
        return repository.findById(id).map(user -> {
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getStateId() != null) {

                StateEntity stateEntity = new StateEntity();
                stateEntity.setId(userDto.getStateId());
                user.setState(stateEntity);
            }
            if (userDto.getRoleIds() != null) {
                List<RoleEntity> roleEntities = userDto.getRoleIds().stream().map(roleId -> {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setId(roleId);
                    return roleEntity;
                }).collect(Collectors.toList());
                user.setRoleEntities(roleEntities);
            }
            return UserDtoMapper.toResponseDto(repository.save(user));
        }).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public Page<UserListResponseDto> searchUsers(String username, String dni, String hospital, UUID id, RoleEntity role, Pageable pageable) {
        if (username == null && dni == null && hospital == null && id == null && role == null) {
            return repository.findAllWithCustomOrder(pageable).map(UserDtoMapper::toListResponseDto);
        }
        return repository.searchUsers(username, dni, hospital, id, role, pageable)
                .map(UserDtoMapper::toListResponseDto);
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
            user.setState(stateEntity);
            repository.save(user);
        });
    }
}