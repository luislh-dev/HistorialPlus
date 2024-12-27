package com.historialplus.historialplus.internal.recorddetail.service;

import com.historialplus.historialplus.auth.AuthService.IAuthService;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.external.facade.CompressAndUploadService.ICompressAndUploadService;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.record.repository.RecordRepository;
import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateRequestDTO;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.internal.recorddetail.repository.RecordDetailRepository;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordDetailServiceImpl implements IRecordDetailService {

    private final RecordDetailRepository recordDetailRepository;
    private final RecordRepository recordRepository;
    private final IAuthService authService;
    private final IUserService userService;
    private final ICompressAndUploadService compressAndUploadService;

    @Override
    public List<RecordDetailResponseDto> findAll() {
        return recordDetailRepository.findAll().stream()
                .map(RecordDetailDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecordDetailResponseDto> findById(UUID id) {
        return recordDetailRepository.findById(id)
                .map(RecordDetailDtoMapper::toResponseDto);
    }

    @Override
    public List<RecordDetailResponseDto> findByRecordId(UUID recordId) {
        return recordDetailRepository.findByRecordId(recordId).stream()
                .map(RecordDetailDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateState(UUID id, Integer stateId) {
        RecordDetailEntity recordDetailEntity = recordDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record detail not found with the provided ID."));
        recordDetailEntity.getState().setId(stateId);
        recordDetailRepository.save(recordDetailEntity);
    }

    @Override
    public Optional<RecordDetailEntity> findEntityById(UUID id) {
        return recordDetailRepository.findById(id);
    }

    @Override
    @Transactional
    public CompletableFuture<RecordDetailResponseDto> save(RecordDetailCreateRequestDTO dto) {
        // Recuperar el usuario autenticado
        String username = authService.getUsername();

        return CompletableFuture.supplyAsync(() -> {

            //Recuperar la persona asociada al usuario, con el nombre
            UserEntity doctor = userService.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

            HospitalEntity hospital = doctor.getHospital();

            RecordEntity record = recordRepository.findById(dto.getRecordId())
                    .orElseThrow(() -> new NotFoundException("Historial no encontrado con ID: " + dto.getRecordId()));

            // Crear el objeto RecordDetailEntity
            RecordDetailEntity detail = new RecordDetailEntity();
            detail.setRecord(record);
            detail.setHospital(hospital);
            detail.setDoctor(doctor);
            detail.setVisitDate(dto.getVisitDate());
            detail.setReason(dto.getReason());
            detail.setDiagnosis(dto.getDiagnosis());
            detail.setTreatment(dto.getTreatment());

            StateEntity state = new StateEntity();
            state.setId(dto.getStateId());
            detail.setState(state);

            // Mapear los archivos
            Set<FileEntity> files = new HashSet<>();
            List<CompletableFuture<FileEntity>> fileFutures = new ArrayList<>();

            // Comprimir y subir los archivos a Cloudflare
            dto.getFiles().forEach(fileDto -> {
                CompletableFuture<FileEntity> fileFuture = compressAndUploadService.compressAndUpload(fileDto.getFile())
                        .thenApply(compressFileDto -> {
                            FileEntity file = new FileEntity();
                            file.setRecordDetail(detail);
                            file.setName(compressFileDto.getName());
                            file.setUrl(compressFileDto.getPreviewUrl());
                            file.setSizeInBytes(compressFileDto.getSizeBytes());
                            file.setMimeType(compressFileDto.getMimeType());

                            FileTypeEntity fileType = new FileTypeEntity();
                            fileType.setId(fileDto.getFileTypeId());
                            file.setFileType(fileType);

                            return file;
                        });
                fileFutures.add(fileFuture);
            });

            // Esperar a que todos los archivos se suban a Cloudflare
            CompletableFuture.allOf(fileFutures.toArray(new CompletableFuture[0])).join();

            // Obtener los archivos
            fileFutures.forEach(future -> files.add(future.join()));

            detail.setFiles(files);
            RecordDetailEntity savedDetail = recordDetailRepository.save(detail);

            return RecordDetailDtoMapper.toResponseDto(savedDetail);
        });
    }
}