package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.entities.HospitalEntity;
import com.historialplus.historialplus.entities.StateEntity;
import com.historialplus.historialplus.repository.HospitalRepository;
import com.historialplus.historialplus.repository.StateRepository;
import lombok.AllArgsConstructor;
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
        HospitalEntity hospital = HospitalDtoMapper.toHospitalEntity(hospitalDto);
        hospital.setState(state);
        HospitalEntity savedHospital = hospitalRepository.save(hospital);

        return HospitalDtoMapper.toHospitalResponseDto(savedHospital);
    }


    @Override
    public void deleteById(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital no encontrado con ID: " + id);
        }
        hospitalRepository.deleteById(id);
    }
}
