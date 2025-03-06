package com.historialplus.historialplus.internal.user.service;

import com.historialplus.historialplus.auth.AuthService.IAuthService;
import com.historialplus.historialplus.common.security.AdminOnly;
import com.historialplus.historialplus.internal.people.service.IPeopleService;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.service.IStateService;
import com.historialplus.historialplus.internal.user.builder.UserCreationCommand;
import com.historialplus.historialplus.internal.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.mapper.UserDtoMapper;
import com.historialplus.historialplus.internal.user.repository.UserRepository;
import com.historialplus.historialplus.internal.user.specification.SearchUserSpecification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.common.constants.RoleConstants.*;
import static com.historialplus.historialplus.common.constants.State.ACTIVE_ID;
import static com.historialplus.historialplus.common.constants.State.DELETED_ID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final IStateService stateService;
    private final IAuthService authService;
    private final IPeopleService peopleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return repository.findAll().stream().map(UserDtoMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDto> findById(@NonNull UUID id) {
        return repository.findById(id).map(UserDtoMapper::toResponseDto);
    }

    @Override
    @Transactional
    public UserResponseDto update(UUID id, UserUpdateDto userDto) {
        // actualizar el usuario
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

        List<String> roles = authentication.getAuthorities().stream().map(Object::toString).toList();

        UserEntity user = repository.findByUsername(authentication.getName()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        //  Si el ordenamiento es por DNI, se ordena por el número de documento
        if (pageable.getSort().getOrderFor("dni") != null) {
            Sort.Order order = pageable.getSort().getOrderFor("dni");
            assert order != null;
            Sort newSort = Sort.by(new Sort.Order(order.getDirection(), "person.documentNumber"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        }

        // Si el ordenamiento es hospitalName, se ordena por el nombre del hospital
        if (pageable.getSort().getOrderFor("hospital") != null) {
            Sort.Order order = pageable.getSort().getOrderFor("hospital");
            assert order != null;
            Sort newSort = Sort.by(new Sort.Order(order.getDirection(), "hospital.name"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        }

        SearchUserSpecification spec = new SearchUserSpecification(null, dni, null, roleId, stateId);

        // Validar de acuerdo a los roles, si el usuario es ADMIN, puede buscar sin restricciones
        if (roles.contains(ROLE_ADMIN)) {
            spec = new SearchUserSpecification(name, dni, hospitalName, roleId, stateId);
        } else if (roles.contains(ROLE_MANAGEMENT)) {
            // Un usuario de gestión solo puede buscar usuarios de su hospital
            spec = new SearchUserSpecification(name, dni, user.getHospital().getName(), roleId, stateId);
        }

        return repository.findAll(spec, pageable).map(UserDtoMapper::toListResponseDto);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional
    @AdminOnly
    public UserResponseDto createManagementUser(ManagementCreationDto userDto) {
        // buscar el ID de la persona por su DNI
        UUID personId = peopleService.findByDocumentNumber(userDto.getPersonDNI())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"))
                .getId();

        // verificar si la persona ya tiene un usuario en este hospital
        Optional<UserEntity> existingUser = repository.findByPersonIdAndHospitalId(personId, userDto.getHospitalId());
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();

            if (user.getState().getId() != ACTIVE_ID) {
                throw new IllegalArgumentException("El usuario existe pero no está activo en este hospital");
            }

            boolean hasManagementRole = user.getRoleEntities().stream()
                    .anyMatch(role -> role.getId().equals(MANAGEMENT_ID));

            if (hasManagementRole) {
                throw new IllegalArgumentException("La persona ya tiene un usuario de gestión en este hospital");
            }

            RoleEntity managementRole = new RoleEntity();
            managementRole.setId(MANAGEMENT_ID);
            user.getRoleEntities().add(managementRole);
            return UserDtoMapper.toResponseDto(repository.save(user));
        }

        // usar el Builder de UserCreationCommand para crear un nuevo usuario
        UserCreationCommand command = new UserCreationCommand.Builder(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getName(),
                personId,
                MANAGEMENT_ID,
                userDto.getStateId()
        ).hospitalId(userDto.getHospitalId()).build();

        UserEntity userEntity = command.toUserEntity();
        userEntity = repository.save(userEntity);

        return UserDtoMapper.toResponseDto(userEntity);
    }

    @Override
    @Transactional
    public UserResponseDto createDoctorUser(DoctorCreationDto userDto) {
        // Recuperar el nombre de usuario autenticado
        String usernameAuth = authService.getUsername();

        // Recuperar el ID del hospital del usuario autenticado
        Integer hospitalId = repository.findByUsername(usernameAuth)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
                .getHospital().getId();

        // Verificar si hay hospitalId
        if (hospitalId == null) {
            throw new IllegalArgumentException("Usuario no tiene hospital asignado");
        }

        // Buscar el ID de la persona por su DNI
        UUID personId = peopleService.findByDocumentNumber(userDto.getPersonDNI())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"))
                .getId();

        // Verificar si la persona ya tiene un usuario en este hospital
        Optional<UserEntity> existingUser = repository.findByPersonIdAndHospitalId(personId, hospitalId);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();

            if (user.getState().getId() != ACTIVE_ID) {
                throw new IllegalArgumentException("El usuario existe pero no está activo en este hospital");
            }

            boolean hasDoctorRole = user.getRoleEntities().stream()
                    .anyMatch(role -> role.getId().equals(DOCTOR_ID));

            if (hasDoctorRole) {
                throw new IllegalArgumentException("La persona ya tiene un usuario doctor en este hospital");
            }

            RoleEntity doctorRole = new RoleEntity();
            doctorRole.setId(DOCTOR_ID);
            user.getRoleEntities().add(doctorRole);
            return UserDtoMapper.toResponseDto(repository.save(user));
        }

        // Usar el Builder de UserCreationCommand para crear un nuevo usuario
        UserCreationCommand command = new UserCreationCommand.Builder(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getName(),
                personId,
                DOCTOR_ID,
                userDto.getStateId()
        ).hospitalId(hospitalId).build();

        UserEntity userEntity = command.toUserEntity();
        userEntity = repository.save(userEntity);

        return UserDtoMapper.toResponseDto(userEntity);
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
        StateEntity state = stateService.findById(DELETED_ID).orElseThrow(() -> new IllegalArgumentException("Estado " + "no encontrado"));
        // recuperar el usuario
        UserEntity user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no " + "encontrado"));
        // cambiar el estado del usuario a eliminado
        user.setState(state);
        repository.save(user);
    }

}