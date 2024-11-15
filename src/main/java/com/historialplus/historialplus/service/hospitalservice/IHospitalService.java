package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import java.util.List;
import java.util.Optional;

public interface IHospitalService {
    List<HospitalResponseDto> findAll();
    Optional<HospitalResponseDto> findById(Integer id);
    HospitalResponseDto save(HospitalCreateDto hospitalDto);
    void deleteById(Integer id);
}
