package com.historialplus.historialplus.recorddetail.controller;

import com.historialplus.historialplus.recorddetail.dto.request.RecordDetailCreateDto;
import com.historialplus.historialplus.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.recorddetail.service.IRecordDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/record-details")
public class RecordDetailController {

    private final IRecordDetailService recordDetailService;

    @Autowired
    public RecordDetailController(IRecordDetailService recordDetailService) {
        this.recordDetailService = recordDetailService;
    }

    @GetMapping
    public ResponseEntity<List<RecordDetailResponseDto>> getAllRecordDetails() {
        List<RecordDetailResponseDto> recordDetails = recordDetailService.findAll();
        return ResponseEntity.ok(recordDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDetailResponseDto> getRecordDetailById(@PathVariable UUID id) {
        return recordDetailService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/record/{recordId}")
    public ResponseEntity<List<RecordDetailResponseDto>> getRecordDetailsByRecordId(@PathVariable UUID recordId) {
        List<RecordDetailResponseDto> recordDetails = recordDetailService.findByRecordId(recordId);
        return ResponseEntity.ok(recordDetails);
    }

    @PostMapping
    public ResponseEntity<RecordDetailResponseDto> createRecordDetail(@RequestBody RecordDetailCreateDto recordDetailCreateDto) {
        RecordDetailResponseDto response = recordDetailService.save(recordDetailCreateDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> updateRecordDetailState(@PathVariable UUID id, @RequestParam Integer stateId) {
        recordDetailService.updateState(id, stateId);
        return ResponseEntity.noContent().build();
    }
}