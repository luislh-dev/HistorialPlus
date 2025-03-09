package com.historialplus.historialplus.internal.people.controller;

import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.service.IPeopleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/getPersonNameByDocument/{id}/{documentNumber}")
    public ResponseEntity<?> getPersonNameByDocument(@PathVariable Integer id, @PathVariable String documentNumber) {
        return service.getPersonNameByDocument(id, documentNumber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findAllWithVisitsStats")
    public Page<?> findAllWithVisitsStats(
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String fullName,
            Pageable pageable
    ) {
        return service.findAllWithVisitsStats(documentNumber, fullName, pageable);
    }

    @GetMapping("/findBasicById/{id}")
    public ResponseEntity<?> findBasicById(@PathVariable UUID id) {
        return service.findBasicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
