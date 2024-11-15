package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHospitalService {
    Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Pageable pageable);
}
