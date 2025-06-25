package com.historialplus.historialplus.internal.sextype.controller;

import com.historialplus.historialplus.internal.sextype.service.SexTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sexType")
public class SexTypeController {
    private final SexTypeService service;

    public SexTypeController(SexTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<?> findAll() {
        return service.findAll();
    }
}
