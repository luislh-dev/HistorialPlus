package com.historialplus.historialplus.internal.state.controller;

import com.historialplus.historialplus.internal.state.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/state")
public class StateController {

    private final StateService service;

    public StateController(StateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<?>> list() {
        return ResponseEntity.ok(service.findAll());
    }

}
