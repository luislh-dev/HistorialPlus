package com.historialplus.historialplus.internal.hospital.controller;

import com.historialplus.historialplus.internal.hospital.dto.request.HospitalCreateDto;
import com.historialplus.historialplus.internal.hospital.dto.request.HospitalUpdateDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalPageResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalResponseDto;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import com.historialplus.historialplus.internal.hospital.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals")
@Validated
public class HospitalController {
    private final HospitalService service;

    public HospitalController(HospitalService service) {
        this.service = service;
    }

    @GetMapping
    public Page<HospitalPageResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String ruc,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer stateId,
            Pageable pageable) {
        return service.findAll(name, ruc, id, stateId, pageable);
    }

    @GetMapping("findByName")
    public Page<HospitalNameProjection> findByName(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return service.findByName(name, pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<HospitalFindByResponseDto> getHospitalById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HospitalCreateDto> createHospital(@Valid @RequestBody HospitalCreateDto hospitalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(hospitalDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<HospitalResponseDto> updateHospital(@PathVariable Integer id, @Valid @RequestBody HospitalUpdateDto hospitalDto) {
        return ResponseEntity.ok(service.update(id, hospitalDto));
    }

}
