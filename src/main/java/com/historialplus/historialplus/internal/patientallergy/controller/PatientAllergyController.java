package com.historialplus.historialplus.internal.patientallergy.controller;

import com.historialplus.historialplus.internal.patientallergy.dto.request.AssignPatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.request.UpdatePatientAllergyDto;
import com.historialplus.historialplus.internal.patientallergy.dto.response.PatientAllergyResponseDto;
import com.historialplus.historialplus.internal.patientallergy.service.PatientAllergyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient-allergies")
@RequiredArgsConstructor
public class PatientAllergyController {

    private final PatientAllergyService patientAllergyService;

    @PostMapping
    public ResponseEntity<PatientAllergyResponseDto> assignAllergy(@Valid @RequestBody AssignPatientAllergyDto dto) {
        PatientAllergyResponseDto response = patientAllergyService.assignToRecord(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/by-record/{recordId}")
    public ResponseEntity<List<PatientAllergyResponseDto>> getByRecordId(@PathVariable UUID recordId) {
        List<PatientAllergyResponseDto> response = patientAllergyService.findByRecordId(recordId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientAllergyResponseDto> updateAssignedAllergy(
            @PathVariable("id") UUID assignedAllergyId,
            @Valid @RequestBody UpdatePatientAllergyDto dto) {
        PatientAllergyResponseDto response = patientAllergyService.update(assignedAllergyId, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PatientAllergyResponseDto> deactivate(@PathVariable("id") UUID assignedAllergyId) {
        return ResponseEntity.ok(patientAllergyService.deactivate(assignedAllergyId));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<PatientAllergyResponseDto> reactivate(@PathVariable("id") UUID assignedAllergyId) {
        return ResponseEntity.ok(patientAllergyService.reactivate(assignedAllergyId));
    }
}
