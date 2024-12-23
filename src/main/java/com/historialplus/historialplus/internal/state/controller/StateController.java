package com.historialplus.historialplus.internal.state.controller;

import com.historialplus.historialplus.internal.state.service.IStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/state")
public class StateController {

    private final IStateService service;

    public StateController(IStateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }

}
