package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.dto.filesDTOs.mapper.FilesDtoMapper;
import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import com.historialplus.historialplus.dto.filesDTOs.response.FilesResponseDto;
import com.historialplus.historialplus.entities.FileEntity;
import com.historialplus.historialplus.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.recorddetail.service.IRecordDetailService;
import com.historialplus.historialplus.service.fileservice.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final IFileService fileService;
    private final IRecordDetailService recordDetailService;

    @Autowired
    public FileController(IFileService fileService, IRecordDetailService recordDetailService) {
        this.fileService = fileService;
        this.recordDetailService = recordDetailService;
    }

    @GetMapping
    public ResponseEntity<List<FilesResponseDto>> getAllFiles() {
        List<FilesResponseDto> files = fileService.findAll();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilesResponseDto> getFileById(@PathVariable UUID id) {
        return fileService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilesResponseDto> createFile(@RequestBody FilesCreateDto fileCreateDto, @RequestParam UUID recordDetailId) {
        RecordDetailEntity parentDetail = recordDetailService.findEntityById(recordDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid record detail ID"));

        FileEntity fileEntity = FilesDtoMapper.toEntity(fileCreateDto, parentDetail);
        FileEntity savedFile = fileService.save(fileEntity);
        FilesResponseDto response = FilesDtoMapper.toResponseDto(savedFile);
        return ResponseEntity.ok(response);
    }
}