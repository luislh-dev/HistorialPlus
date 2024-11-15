package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.dto.peopleDTOs.mapper.PeopleDtoMapper;
import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.entities.*;
import com.historialplus.historialplus.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class HospitalServiceImpl implements IHospitalService {

    private final HospitalRepository hospitalRepository;
    private final StateRepository stateRepository;
    private final PeopleRepository peopleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SexTypeRepository sexTypeRepository;
    private final TypeDocumentRepository typeDocumentRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, StateRepository stateRepository, PeopleRepository peopleRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, SexTypeRepository sexTypeRepository, TypeDocumentRepository typeDocumentRepository) {
        this.hospitalRepository = hospitalRepository;
        this.stateRepository = stateRepository;
        this.peopleRepository = peopleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.sexTypeRepository = sexTypeRepository;
        this.typeDocumentRepository = typeDocumentRepository;
    }
    @Override
    public Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Pageable pageable) {
        if ((name == null || name.isEmpty()) && (ruc == null || ruc.isEmpty()) && id == null) {
            return hospitalRepository.findAll(pageable).map(HospitalDtoMapper::toHospitalResponseDto);
        }
        return hospitalRepository.findByNameContainingOrRucContainingOrId(name, ruc, id, pageable)
                .map(HospitalDtoMapper::toHospitalResponseDto);
    }

    @Override
    public Optional<HospitalResponseDto> findById(Integer id) {
        return hospitalRepository.findById(id)
                .map(HospitalDtoMapper::toHospitalResponseDto);
    }

    @Override
    public HospitalResponseDto save(HospitalCreateDto hospitalDto) {
        // Validar y obtener el estado
        StateEntity state = stateRepository.findById(hospitalDto.getStateId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + hospitalDto.getStateId()));

        // Crear y guardar el hospital
        HospitalEntity hospital = new HospitalEntity();
        hospital.setName(hospitalDto.getName());
        hospital.setPhone(hospitalDto.getPhone());
        hospital.setEmail(hospitalDto.getEmail());
        hospital.setRuc(hospitalDto.getRuc());
        hospital.setState(state);
        HospitalEntity savedHospital = hospitalRepository.save(hospital);

        // Crear el usuario administrador y la persona vinculada
        if (hospitalDto.getAdminUser() != null && hospitalDto.getAdminPerson() != null) {
            createAdminUserAndPerson(hospitalDto.getAdminUser(), hospitalDto.getAdminPerson(), savedHospital);
        }

        return HospitalDtoMapper.toHospitalResponseDto(savedHospital);
    }

    private void createAdminUserAndPerson(UserCreateDto adminUserDto, PeopleCreateDto adminPersonDto, HospitalEntity hospital) {
        // usar el mapper
        var person = PeopleDtoMapper.toPeopleEntity(adminPersonDto);

        // Obtener el tipo de sexo desde el ID
        SexTypeEntity sexType = sexTypeRepository.findById(adminPersonDto.getSexTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo de sexo no encontrado con ID: " + adminPersonDto.getSexTypeId()));
        person.setSexType(sexType);

        // Obtener el tipo de documento desde el ID
        TypeDocumentEntity typeDocument = typeDocumentRepository.findById(adminPersonDto.getTypeDocumentId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado con ID: " + adminPersonDto.getTypeDocumentId()));
        person.setTypeDocument(typeDocument);

        // Guardar la persona en la base de datos
        PeopleEntity savedPerson = peopleRepository.save(person);

        // Crear usuario
        UserEntity user = new UserEntity();
        user.setName(adminUserDto.getName());
        user.setEmail(adminUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        user.setHospital(hospital);
        user.getPeople().add(savedPerson);

        // Asignar rol de administrador
        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol de administrador no encontrado"));
        user.getRoleEntities().add(adminRole);

        // Guardar el usuario
        userRepository.save(user);
    }


    @Override
    public void deleteById(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital no encontrado con ID: " + id);
        }
        hospitalRepository.deleteById(id);
    }
}
