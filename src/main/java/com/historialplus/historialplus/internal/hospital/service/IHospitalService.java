package com.historialplus.historialplus.internal.hospital.service;

import com.historialplus.historialplus.internal.hospital.dto.request.HospitalCreateDto;
import com.historialplus.historialplus.internal.hospital.dto.request.HospitalUpdateDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalPageResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHospitalService {
    Page<HospitalPageResponseDto> findAll(String name, String ruc, Integer id, Integer stateId, Pageable pageable);
    Optional<HospitalFindByResponseDto> findById(Integer id);
    HospitalCreateDto save(HospitalCreateDto hospitalDto);
    void deleteById(Integer id);
    HospitalResponseDto update(Integer id, HospitalUpdateDto hospitalDto);
    Page<HospitalNameProjection> findByName(String name, Pageable pageable);
}
