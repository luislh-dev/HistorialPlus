package com.historialplus.historialplus.internal.documenttype.controller;

import com.historialplus.historialplus.internal.documenttype.dto.DocumentTypeDTO;
import com.historialplus.historialplus.internal.documenttype.service.DocumentTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documentType")
public class DocumentTypeController {

    private  final DocumentTypeService service;

    public DocumentTypeController(DocumentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<DocumentTypeDTO> findAll() {
        return service.findAll();
    }

}
