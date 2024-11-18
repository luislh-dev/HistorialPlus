package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalUpdateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.entities.HospitalEntity;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.repository.HospitalRepository;
import com.historialplus.historialplus.repository.StateRepository;
import com.historialplus.historialplus.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements IHospitalService {

    private final HospitalRepository hospitalRepository;
    private final StateRepository stateRepository;

    @Override
    public Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Pageable pageable) {
        if ((name == null || name.isEmpty()) && (ruc == null || ruc.isEmpty()) && id == null) {
            return hospitalRepository.findAllByOrderByUpdatedAtDesc(pageable).map(HospitalDtoMapper::toHospitalResponseDto);
        }
        return hospitalRepository.findByNameContainingOrRucContainingOrIdOrderByUpdatedAtDesc(name, ruc, id, pageable)
                .map(HospitalDtoMapper::toHospitalResponseDto);
    }

    @Override
    public Optional<HospitalFindByResponseDto> findById(Integer id) {
        return hospitalRepository.findById(id)
                .map(HospitalDtoMapper::toUserFindByResponseDto);
    }

    @Override
    public HospitalCreateDto save(HospitalCreateDto hospitalDto) {
        // Validar y obtener el estado
        StateEntity state = stateRepository.findById(hospitalDto.getStateId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + hospitalDto.getStateId()));

        // Crear y guardar el hospital
        HospitalEntity hospital = HospitalDtoMapper.toHospitalEntity(hospitalDto);
        hospital.setState(state);
        hospitalRepository.save(hospital);

        return hospitalDto;
    }


    @Override
    public void deleteById(Integer id) {
        HospitalEntity hospital = hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital no encontrado con ID: " + id));
        // eliminar logicamente
        hospital.setState(stateRepository.findById(2).orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: 3")));

        hospitalRepository.save(hospital);
    }

    @Override
    public HospitalResponseDto update(Integer id, HospitalUpdateDto hospitalDto) {
        return hospitalRepository.findById(id).map(hospital -> {
            if (hospitalDto.getName() != null) {
                hospital.setName(hospitalDto.getName());
            }
            if (hospitalDto.getAddress() != null) {
                hospital.setAddress(hospitalDto.getAddress());
            }
            if (hospitalDto.getPhone() != null) {
                hospital.setPhone(hospitalDto.getPhone());
            }
            if (hospitalDto.getEmail() != null) {
                hospital.setEmail(hospitalDto.getEmail());
            }
            if (hospitalDto.getRuc() != null) {
                hospital.setRuc(hospitalDto.getRuc());
            }
            if (hospitalDto.getStateId() != null) {
                StateEntity stateEntity = new StateEntity();
                stateEntity.setId(hospitalDto.getStateId());
                hospital.setState(stateEntity);
            }
            return HospitalDtoMapper.toHospitalResponseDto(hospitalRepository.save(hospital));
        }).orElseThrow(() -> new RuntimeException("Hospital no encontrado con ID: " + id));
    }
}
