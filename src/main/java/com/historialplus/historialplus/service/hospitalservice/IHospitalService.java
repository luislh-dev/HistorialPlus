package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalUpdateDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHospitalService {
    Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Integer stateId, Pageable pageable);
    Optional<HospitalFindByResponseDto> findById(Integer id);
    HospitalCreateDto save(HospitalCreateDto hospitalDto);
    void deleteById(Integer id);
    HospitalResponseDto update(Integer id, HospitalUpdateDto hospitalDto);
}
