package com.historialplus.historialplus.internal.people.controller;

import com.historialplus.historialplus.common.enums.DocumentTypeEnum;
import com.historialplus.historialplus.common.validators.document.DocumentValid;
import com.historialplus.historialplus.internal.people.dto.request.PeopleCreateDto;
import com.historialplus.historialplus.internal.people.dto.response.MinimalPeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PeopleResponseDto;
import com.historialplus.historialplus.internal.people.dto.response.PersonBasicDTO;
import com.historialplus.historialplus.internal.people.presenters.PeopleRecordPresenter;
import com.historialplus.historialplus.internal.people.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/people")
@Validated
public class PeopleController {

    private final PeopleService service;

    public PeopleController(PeopleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PeopleResponseDto> save(@Valid @RequestBody PeopleCreateDto peopleCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(peopleCreateDto));
    }

    @GetMapping("getPersonName/{dni}")
    public ResponseEntity<MinimalPeopleResponseDto> getPersonName(@PathVariable String dni) {
        return service.getPersonName(dni)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("getPersonNameByDocument/{documentType}/{documentNumber}")
    public ResponseEntity<MinimalPeopleResponseDto> getPersonNameByDocument(@PathVariable DocumentTypeEnum documentType, @PathVariable @DocumentValid String documentNumber) {
        return service.getPersonNameByDocument(documentType, documentNumber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("findAllWithVisitsStats")
    public Page<PeopleRecordPresenter> findAllWithVisitsStats(
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String fullName,
            Pageable pageable
    ) {
        return service.findAllWithVisitsStats(documentNumber, fullName, pageable);
    }

    @GetMapping("findBasicById/{id}")
    public ResponseEntity<PersonBasicDTO> findBasicById(@PathVariable UUID id) {
        return service.findBasicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
