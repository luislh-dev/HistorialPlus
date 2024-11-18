package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.recordDTOs.request.RecordCreateDto;
import com.historialplus.historialplus.dto.recordDTOs.response.RecordResponseDto;
import com.historialplus.historialplus.service.recordservice.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{documentNumber}")
    public ResponseEntity<RecordResponseDto> getRecordByDocumentNumber(@PathVariable String documentNumber) {
        UUID personId = recordService.findPersonIdByDocumentNumber(documentNumber);
        return recordService.findById(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRecord(@RequestBody RecordCreateDto recordCreateDto) {
    return ResponseEntity.ok(recordService.save(recordCreateDto));
    }
}