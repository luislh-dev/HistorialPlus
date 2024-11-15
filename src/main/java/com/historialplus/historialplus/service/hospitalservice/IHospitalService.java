package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import java.util.List;
import java.util.Optional;
import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHospitalService {
    Page<HospitalResponseDto> findAll(String name, String ruc, Integer id, Pageable pageable);
    Optional<HospitalResponseDto> findById(Integer id);
    HospitalResponseDto save(HospitalCreateDto hospitalDto);
    void deleteById(Integer id);
}
