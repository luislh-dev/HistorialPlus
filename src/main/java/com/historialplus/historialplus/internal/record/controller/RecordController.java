package com.historialplus.historialplus.internal.record.controller;

import com.historialplus.historialplus.internal.record.dto.response.RecordResponseDto;
import com.historialplus.historialplus.internal.record.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final IRecordService recordService;

    @Autowired
    public RecordController(IRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public ResponseEntity<List<RecordResponseDto>> getAllRecords() {
        List<RecordResponseDto> records = recordService.findAll();
        return ResponseEntity.ok(records);
    }

    @GetMapping("{documentNumber}")
    public ResponseEntity<RecordResponseDto> getRecordByDocumentNumber(@PathVariable String documentNumber) {
        UUID personId = recordService.findPersonIdByDocumentNumber(documentNumber);
        return recordService.findById(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}