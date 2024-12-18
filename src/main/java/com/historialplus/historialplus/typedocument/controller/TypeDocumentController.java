package com.historialplus.historialplus.typedocument.controller;

import com.historialplus.historialplus.typedocument.service.ITypeDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/typeDocument")
public class TypeDocumentController {

    private  final ITypeDocumentService service;

    public TypeDocumentController(ITypeDocumentService service) {
        this.service = service;
    }

    @GetMapping
    public List<?> findAll() {
        return service.findAll();
    }

}
