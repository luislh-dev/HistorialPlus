package com.historialplus.historialplus.internal.allergycatalog.controller;

import com.historialplus.historialplus.internal.allergycatalog.dto.request.AllergyCatalogRequestDto;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogDto;
import com.historialplus.historialplus.internal.allergycatalog.service.AllergyCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/allergy-catalog")
@RequiredArgsConstructor
public class AllergyCatalogController {
    private final AllergyCatalogService allergyCatalogService;

    @PostMapping
    public ResponseEntity<AllergyCatalogDto> create(@Valid @RequestBody AllergyCatalogRequestDto dto) {
        AllergyCatalogDto createdDto = allergyCatalogService.create(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AllergyCatalogDto>> getAllActive(Pageable pageable) {
        return ResponseEntity.ok(allergyCatalogService.findAllActive(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergyCatalogDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(allergyCatalogService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllergyCatalogDto> update(@PathVariable UUID id, @Valid @RequestBody AllergyCatalogRequestDto dto) {
        return ResponseEntity.ok(allergyCatalogService.update(id, dto));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AllergyCatalogDto> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(allergyCatalogService.deactivate(id));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<AllergyCatalogDto> reactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(allergyCatalogService.reactivate(id));
    }
}
