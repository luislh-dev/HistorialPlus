package com.historialplus.historialplus.internal.filetype.controller;

import com.historialplus.historialplus.internal.filetype.service.FileTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-type")
@RequiredArgsConstructor
public class FileTypeController {

    private final FileTypeService service;

    @GetMapping
    public ResponseEntity<?> getAllFileTypes() {
        return ResponseEntity.ok(service.getAll());
    }

}
