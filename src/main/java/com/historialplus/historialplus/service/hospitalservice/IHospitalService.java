package com.historialplus.historialplus.service.hospitalservice;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalListDto;

import java.util.List;

public interface IHospitalService {
    List<HospitalListDto> findAll();
}
