package com.historialplus.historialplus.internal.allergy.controller;

import com.historialplus.historialplus.internal.allergy.dto.request.AllergyCreateDto;
import com.historialplus.historialplus.internal.allergy.dto.request.AllergyUpdateDto;
import com.historialplus.historialplus.internal.allergy.dto.response.AllergyResponseDto;
import com.historialplus.historialplus.internal.allergy.projection.AllergyDetailsProjection;
import com.historialplus.historialplus.internal.allergy.projection.AllergyPageProjection;
import com.historialplus.historialplus.internal.allergy.service.AllergyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/allergies")
@RequiredArgsConstructor
public class AllergyController {
    private final AllergyService allergyService;

    @PostMapping
    public ResponseEntity<AllergyResponseDto> createAllergy(@Valid @RequestBody AllergyCreateDto dto) {
        AllergyResponseDto createdAllergy = allergyService.createAllergy(dto);
        return new ResponseEntity<>(createdAllergy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllergyResponseDto> updateAllergy(@PathVariable Integer id, @Valid @RequestBody AllergyUpdateDto dto) {
        AllergyResponseDto updatedAllergy = allergyService.updateAllergy(id, dto);
        return ResponseEntity.ok(updatedAllergy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergyDetailsProjection> getAllergyById(@PathVariable Integer id) {
        AllergyDetailsProjection allergy = allergyService.findAllergyById(id);
        return ResponseEntity.ok(allergy);
    }

    @GetMapping
    public ResponseEntity<Page<AllergyPageProjection>> getAllAllergies(Pageable pageable) {
        Page<AllergyPageProjection> allergies = allergyService.findAllAllergies(pageable);
        return ResponseEntity.ok(allergies);
    }

    @GetMapping("/by-people/{peopleId}")
    public ResponseEntity<Page<AllergyPageProjection>> getAllergiesByPeopleId(
            @PathVariable UUID peopleId, // *** CAMBIO DE TIPO ***
            Pageable pageable) {
        Page<AllergyPageProjection> allergies = allergyService.findAllergiesByPeopleId(peopleId, pageable);
        return ResponseEntity.ok(allergies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable Integer id) {
        allergyService.deleteAllergy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AllergyResponseDto> toggleAllergyStatus(@PathVariable Integer id, @RequestParam Boolean isActive) {
        AllergyResponseDto updatedAllergy = allergyService.toggleAllergyStatus(id, isActive);
        return ResponseEntity.ok(updatedAllergy);
    }
}