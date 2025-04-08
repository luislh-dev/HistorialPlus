package com.historialplus.historialplus.internal.hospital.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.internal.hospital.dto.request.HospitalCreateDto;
import com.historialplus.historialplus.internal.hospital.dto.request.HospitalUpdateDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalPageResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.hospital.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.internal.hospital.mapper.HospitalMapper;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import com.historialplus.historialplus.internal.hospital.repository.HospitalRepository;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.state.service.IStateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements IHospitalService {

    private final HospitalRepository hospitalRepository;
    private final IStateService stateService;
    private final HospitalMapper mapper;

    @Override
    public Page<HospitalPageResponseDto> findAll(String name, String ruc, Integer id, Integer stateId, Pageable pageable) {
        return hospitalRepository.findAllWithProjection(name, ruc, id, stateId, pageable).map(mapper::hospitalPageProjectionToHospitalPageResponseDto);
    }

    @Override
    public Optional<HospitalFindByResponseDto> findById(Integer id) {
        return hospitalRepository.findProjectedById(id).map(mapper::hospitalDetailsProjectionToHospitalFindByResponseDto);
    }

    @Override
    public HospitalCreateDto save(HospitalCreateDto hospitalDto) {
        // Validar y obtener el estado
        StateEntity state = stateService.findById(hospitalDto.getStateId())
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

        hospital.setState(stateService.findByName(StateEnum.DELETED).orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id)));

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

    @Override
    public Page<HospitalNameProjection> findByName(String name, Pageable pageable) {
        return hospitalRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}
