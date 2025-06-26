package com.historialplus.historialplus.internal.documenttype.controller;

import com.historialplus.historialplus.internal.documenttype.projection.TypeDocumentProjection;
import com.historialplus.historialplus.internal.documenttype.service.TypeDocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documentType")
public class TypeDocumentController {

    private  final TypeDocumentService service;

    public TypeDocumentController(TypeDocumentService service) {
        this.service = service;
    }

    @GetMapping
    public List<TypeDocumentProjection> findAll() {
        return service.findAll();
    }

}
