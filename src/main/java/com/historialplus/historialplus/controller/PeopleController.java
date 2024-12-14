package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.peopleDTOs.request.PeopleCreateDto;
import com.historialplus.historialplus.service.peopleservice.IPeopleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
@Validated
public class PeopleController {

    private final IPeopleService service;

    public PeopleController(IPeopleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PeopleCreateDto peopleCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(peopleCreateDto));
    }

    @GetMapping("/getPersonName/{dni}")
    public ResponseEntity<?> getPersonName(@PathVariable String dni) {
        return ResponseEntity.ok(service.getPersonName(dni));
    }

}
