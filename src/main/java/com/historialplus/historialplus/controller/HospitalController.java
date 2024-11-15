package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.hospitalDTOs.response.HospitalResponseDto;
import com.historialplus.historialplus.dto.hospitalDTOs.request.HospitalCreateDto;
import com.historialplus.historialplus.service.hospitalservice.IHospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDto> getHospitalById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HospitalResponseDto> createHospital(@RequestBody HospitalCreateDto hospitalDto) {
        HospitalResponseDto savedHospital = service.save(hospitalDto);
        return ResponseEntity.ok(savedHospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
