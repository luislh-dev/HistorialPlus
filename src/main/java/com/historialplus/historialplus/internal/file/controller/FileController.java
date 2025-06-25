package com.historialplus.historialplus.internal.file.controller;

import com.historialplus.historialplus.internal.file.dto.request.FilesCreateDto;
import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.mapper.FilesDtoMapper;
import com.historialplus.historialplus.internal.file.service.FileService;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.service.RecordDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    private final RecordDetailService recordDetailService;

    @GetMapping
    public ResponseEntity<List<FilesResponseDto>> getAllFiles() {
        List<FilesResponseDto> files = fileService.findAll();
        return ResponseEntity.ok(files);
    }

    @GetMapping("{id}")
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