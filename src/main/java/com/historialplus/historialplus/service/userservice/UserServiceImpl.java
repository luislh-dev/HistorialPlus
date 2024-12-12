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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.constants.RoleConstants.ROLE_ADMIN;
import static com.historialplus.historialplus.constants.RoleConstants.ROLE_MANAGEMENT;
import static com.historialplus.historialplus.constants.State.DELETED_ID;

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
        return UserDtoMapper.toDto(repository.save(userDtoMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public UserResponseDto createHospitalUserByManagement(UserCreateDto userDto) {
        // Obtener el usuario de gestión autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity managementUser = repository.findByUsername(authentication.getName())
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
                user.setUsername(userDto.getName());
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
    public Page<UserListResponseDto> searchUsers(String name, String dni, String hospitalName, Integer roleId, Integer stateId, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = repository.findByUsername(authentication.getName()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String role = user.getRoleEntities().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")).getName();

        //  Si el ordenamiento es por DNI, se ordena por el número de documento
        if (pageable.getSort().getOrderFor("dni") != null) {
            Sort.Order order = pageable.getSort().getOrderFor("dni");
            assert order != null;
            Sort newSort = Sort.by(new Sort.Order(order.getDirection(), "person.documentNumber"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        }

        // Si el hordenamiento es hospitalName, se ordena por el nombre del hospital
        if (pageable.getSort().getOrderFor("hospital") != null) {
            Sort.Order order = pageable.getSort().getOrderFor("hospital");
            assert order != null;
            Sort newSort = Sort.by(new Sort.Order(order.getDirection(), "hospital.name"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        }

        SearchUserSpecification spec = new SearchUserSpecification(null, dni, null, roleId, stateId);

        if (role.equals(ROLE_ADMIN)) {
            // Un administrador puede buscar sin restricciones
            spec = new SearchUserSpecification(name, dni, hospitalName, roleId, stateId);
        } else if (role.equals(ROLE_MANAGEMENT)) {
            // Un usuario de gestión solo puede buscar usuarios de su hospital
            spec = new SearchUserSpecification(name, dni, user.getHospital().getName(), roleId, stateId);
        }

        return repository.findAll(spec, pageable).map(UserDtoMapper::toListResponseDto);
    }


    /**
     * Elimina un usuario por su ID
     * El estado del usuario se cambia a eliminado (ID: 3)
     *
     * @param id ID del usuario a eliminar
     */
    @Override
    @Transactional
    public void deleteById(UUID id) {
        // validar si el estado existe DELETED_ID
        StateEntity state = stateService.findById(DELETED_ID).orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
        // recuperar el usuario
        UserEntity user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        // cambiar el estado del usuario a eliminado
        user.setState(state);
        repository.save(user);
    }
}