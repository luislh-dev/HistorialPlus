package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.mapper.HospitalDtoMapper;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalListDto;
import com.historialplus.historialplus.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements IHospitalService {

    private final HospitalRepository repository;

    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HospitalListDto> findAll() {
        return repository.findAll().stream().map(HospitalDtoMapper::toHospitalListDto).toList();
    }
}
