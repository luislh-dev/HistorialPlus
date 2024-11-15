package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.repository.HospitalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements IHospitalService {

    private final HospitalRepository repository;

    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Pageable pageable) {
        if ((name == null || name.isEmpty()) && (ruc == null || ruc.isEmpty()) && id == null) {
            return repository.findAll(pageable).map(HospitalDtoMapper::toHospitalListDto);
        }
        return repository.findByNameContainingOrRucContainingOrId(name, ruc, id, pageable)
                .map(HospitalDtoMapper::toHospitalListDto);
    }
}
