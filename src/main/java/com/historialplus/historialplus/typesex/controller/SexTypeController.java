package com.historialplus.historialplus.typesex.controller;

import com.historialplus.historialplus.typesex.service.ISexTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sexType")
public class SexTypeController {
    private final ISexTypeService service;

    public SexTypeController(ISexTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<?> findAll() {
        return service.findAll();
    }
}
