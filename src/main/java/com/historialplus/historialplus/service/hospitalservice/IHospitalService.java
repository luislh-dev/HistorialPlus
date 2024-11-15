package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;

import java.util.List;

public interface IHospitalService {
    List<HospitalResponseDto> findAll();
}
