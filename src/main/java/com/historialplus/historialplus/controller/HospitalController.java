package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.service.hospitalservice.IHospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final IHospitalService service;

    public HospitalController(IHospitalService service) {
        this.service = service;
    }

    @GetMapping
    public List<HospitalResponseDto> list() {
        return service.findAll();
    }

}
