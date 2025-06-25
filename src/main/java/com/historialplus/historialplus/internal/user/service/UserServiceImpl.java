package com.historialplus.historialplus.internal.user.service;

import com.historialplus.historialplus.auth.AuthService.AuthService;
import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.people.service.PeopleService;
import com.historialplus.historialplus.internal.role.entites.RoleEntity;
import com.historialplus.historialplus.internal.role.service.RoleService;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.service.StateService;
import com.historialplus.historialplus.internal.user.builder.UserCreationCommand;
import com.historialplus.historialplus.internal.user.dto.request.DoctorCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.ManagementCreationDto;
import com.historialplus.historialplus.internal.user.dto.request.UserUpdateDto;
import com.historialplus.historialplus.internal.user.dto.response.UserListResponseDto;
import com.historialplus.historialplus.internal.user.dto.response.UserResponseDto;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.mapper.UserMapper;
import com.historialplus.historialplus.internal.user.repository.UserRepository;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_ADMIN;
import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_DOCTOR;
import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_MANAGEMENT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final StateService stateService;
    private final AuthService authService;
    private final PeopleService peopleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper mapper;

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
            return mapper.userEntityToUserResponseDto(repository.save(user));
        }).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public Page<UserListResponseDto> searchUsers(String name, String dni, String hospitalName, Integer roleId, Integer stateId, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = authentication.getAuthorities().stream().map(Object::toString).toList();

        if (roles.contains(ROLE_MANAGEMENT.name()) && !roles.contains(ROLE_ADMIN.name())) {
            UserEntity user = repository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            hospitalName = user.getHospital().getName();
        }

        Sort sort = pageable.getSort();
        if (sort.getOrderFor("dni") != null) {
            Sort.Order order = sort.getOrderFor("dni");
            sort = Sort.by(new Sort.Order(Objects.requireNonNull(order).getDirection(), "u.person.documentNumber"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        if (sort.getOrderFor("hospital") != null) {
            Sort.Order order = sort.getOrderFor("hospital");
            sort = Sort.by(new Sort.Order(Objects.requireNonNull(order).getDirection(), "u.hospital.name"));
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return repository.findByFilters(name, dni, hospitalName, roleId, stateId, pageable).map(mapper::toUserListResponseDto);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserResponseDto createManagementUser(ManagementCreationDto userDto) {
        // buscar el ID de la persona por su DNI
        UUID personId = peopleService.findByDocumentNumber(userDto.getPersonDNI())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"))
                .getId();

        // verificar si la persona ya tiene un usuario en este hospital
        Optional<UserEntity> existingUser = repository.findByPersonIdAndHospitalId(personId, userDto.getHospitalId());
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();

            if (user.getState().getName() != StateEnum.ACTIVE) {
                throw new IllegalArgumentException("El usuario existe pero no está activo en este hospital");
            }

            boolean hasManagementRole = user.getRoleEntities().stream()
                    .anyMatch(role -> role.getName().equals(ROLE_MANAGEMENT));

            if (hasManagementRole) {
                throw new IllegalArgumentException("La persona ya tiene un usuario de gestión en este hospital");
            }

            RoleEntity managementRole = roleService.findByName(ROLE_MANAGEMENT)
                .orElseThrow(() -> new IllegalArgumentException("Rol de gestión no encontrado"));
            user.getRoleEntities().add(managementRole);
            return mapper.userEntityToUserResponseDto(repository.save(user));
        }

        RoleEntity managementRole = roleService.findByName(ROLE_MANAGEMENT)
            .orElseThrow(() -> new IllegalArgumentException("Rol de gestión no encontrado"));

        // usar el Builder de UserCreationCommand para crear un nuevo usuario
        UserCreationCommand command = new UserCreationCommand.Builder(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getName(),
                personId,
                managementRole.getId(),
                userDto.getStateId()
        ).hospitalId(userDto.getHospitalId()).build();

        UserEntity userEntity = command.toUserEntity();
        userEntity = repository.save(userEntity);

        return mapper.userEntityToUserResponseDto(userEntity);
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

            if (user.getState().getName() != StateEnum.ACTIVE) {
                throw new IllegalArgumentException("El usuario existe pero no está activo en este hospital");
            }

            boolean hasDoctorRole = user.getRoleEntities().stream()
                    .anyMatch(role -> role.getName().equals(ROLE_DOCTOR));

            if (hasDoctorRole) {
                throw new IllegalArgumentException("La persona ya tiene un usuario doctor en este hospital");
            }

            RoleEntity doctorRole = roleService.findByName(ROLE_DOCTOR)
                .orElseThrow(() -> new IllegalArgumentException("Rol de doctor no encontrado"));
            user.getRoleEntities().add(doctorRole);
            return mapper.userEntityToUserResponseDto(repository.save(user));
        }

        RoleEntity doctorRole = roleService.findByName(ROLE_DOCTOR)
            .orElseThrow(() -> new IllegalArgumentException("Rol de doctor no encontrado"));

        // Usar el Builder de UserCreationCommand para crear un nuevo usuario
        UserCreationCommand command = new UserCreationCommand.Builder(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getName(),
                personId,
                doctorRole.getId(),
                userDto.getStateId()
        ).hospitalId(hospitalId).build();

        UserEntity userEntity = command.toUserEntity();
        userEntity = repository.save(userEntity);

        return mapper.userEntityToUserResponseDto(userEntity);
    }

    /**
     * Elimina un usuario por su ID
     * El estado del usuario se cambia ha eliminado (DELETED)
     *
     * @param id ID del usuario a eliminar
     */
    @Override
    @Transactional
    public void deleteById(UUID id) {
        StateEntity state = stateService.findByName(StateEnum.DELETED).orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
        // recuperar el usuario
        UserEntity user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no " + "encontrado"));
        // cambiar el estado del usuario a eliminado
        user.setState(state);
        repository.save(user);
    }

}