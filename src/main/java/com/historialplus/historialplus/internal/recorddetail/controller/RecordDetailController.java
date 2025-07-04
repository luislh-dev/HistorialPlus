package com.historialplus.historialplus.internal.recorddetail.controller;

import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateRequestDTO;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.presenters.RecordDetailPresenter;
import com.historialplus.historialplus.internal.recorddetail.service.RecordDetailService;
import com.historialplus.historialplus.internal.recorddetail.viewmodel.RecordDetailExtenseViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/record-details")
public class RecordDetailController {

    private final RecordDetailService recordDetailService;

    @Autowired
    public RecordDetailController(RecordDetailService recordDetailService) {
        this.recordDetailService = recordDetailService;
    }

    @GetMapping
    public ResponseEntity<List<RecordDetailResponseDto>> getAllRecordDetails() {
        List<RecordDetailResponseDto> recordDetails = recordDetailService.findAll();
        return ResponseEntity.ok(recordDetails);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecordDetailResponseDto> getRecordDetailById(@PathVariable UUID id) {
        return recordDetailService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("record/{recordId}")
    public ResponseEntity<List<RecordDetailResponseDto>> getRecordDetailsByRecordId(@PathVariable UUID recordId) {
        List<RecordDetailResponseDto> recordDetails = recordDetailService.findByRecordId(recordId);
        return ResponseEntity.ok(recordDetails);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecordDetailResponseDto> createRecordDetail(
            @RequestParam("personId") UUID personId,
            @RequestParam("stateId") Integer stateId,
            @RequestParam("reason") String reason,
            @RequestParam(required = false) String diagnosis,
            @RequestParam(required = false) String treatment,
            @RequestParam("visitDate") String visitDate,
            @RequestParam("fileTypeIds") Integer[] fileTypeIds,
            @RequestParam("files") MultipartFile[] files
    ) throws ExecutionException, InterruptedException {
        RecordDetailCreateRequestDTO dto = new RecordDetailCreateRequestDTO();
        dto.setPersonId(personId);
        dto.setStateId(stateId);
        dto.setReason(reason);
        dto.setDiagnosis(diagnosis);
        dto.setTreatment(treatment);
        dto.setVisitDate(LocalDateTime.parse(visitDate));

        HashSet<RecordDetailCreateRequestDTO.FileDTO> hashSet = new HashSet<>();

        for (int i = 0; i < files.length; i++) {
            RecordDetailCreateRequestDTO.FileDTO fileDTO = new RecordDetailCreateRequestDTO.FileDTO();
            fileDTO.setFile(files[i]);
            fileDTO.setFileTypeId(fileTypeIds[i]);
            hashSet.add(fileDTO);
        }

        dto.setFiles(hashSet);

        CompletableFuture<RecordDetailResponseDto> future = recordDetailService.save(dto);
        RecordDetailResponseDto result = future.get();
        return ResponseEntity.ok(result);
    }

    @GetMapping("findAllByPeopleId/{peopleId}")
    public Page<RecordDetailPresenter> findAllByPeopleId(
            @PathVariable UUID peopleId,
            @RequestParam(required = false) String hospitalName,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Pageable pageable) {
        return recordDetailService.getRecordDetails(peopleId, hospitalName, startDate, endDate, pageable);
    }

    @GetMapping("FindById/{recordDetailId}")
    public ResponseEntity<RecordDetailExtenseViewModel> getRecordDetail(@PathVariable UUID recordDetailId) {
        return ResponseEntity.ok(recordDetailService.getRecordDetail(recordDetailId));
    }
}