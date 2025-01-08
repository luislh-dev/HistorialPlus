package com.historialplus.historialplus.internal.recorddetail.service;

import com.historialplus.historialplus.auth.AuthService.IAuthService;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import com.historialplus.historialplus.external.facade.CompressAndUploadService.ICompressAndUploadService;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;
import com.historialplus.historialplus.internal.file.projection.FileBasicProjection;
import com.historialplus.historialplus.internal.file.repository.FileRepository;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.record.repository.RecordRepository;
import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateRequestDTO;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.internal.recorddetail.presenters.RecordDetailPresenter;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailExtenseProjection;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailProjection;
import com.historialplus.historialplus.internal.recorddetail.repository.RecordDetailRepository;
import com.historialplus.historialplus.internal.recorddetail.viewmodel.RecordDetailExtenseViewModel;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import com.historialplus.historialplus.internal.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.internal.recorddetail.mapper.RecordDetailDtoMapper.toPresenter;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordDetailServiceImpl implements IRecordDetailService {

    private final RecordDetailRepository recordDetailRepository;
    private final RecordRepository recordRepository;
    private final IAuthService authService;
    private final IUserService userService;
    private final ICompressAndUploadService compressAndUploadService;
    private final FileRepository fileRepository;

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
    public CompletableFuture<RecordDetailResponseDto> save(RecordDetailCreateRequestDTO dto) {
        // Recuperar el usuario autenticado
        String username = authService.getUsername();

        return CompletableFuture.supplyAsync(() -> {
            UserEntity doctor = userService.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + username));

            HospitalEntity hospital = doctor.getHospital();

            //Recuperar el historial del paciente, por su id
            RecordEntity record = recordRepository.findByPersonId(dto.getPersonId())
                    .orElseThrow(() -> new NotFoundException("Historial no encontrado para la persona con id: " + dto.getPersonId()));

            RecordDetailEntity detail = createRecordDetail(dto, doctor, hospital, record);
            Set<FileEntity> files = processFiles(dto.getFiles(), detail);
            detail.setFiles(files);

            RecordDetailEntity savedDetail = recordDetailRepository.save(detail);
            return RecordDetailDtoMapper.toResponseDto(savedDetail);
        });
    }


    @Override
    public Page<RecordDetailPresenter> getRecordDetails(UUID peopleId, String hospitalName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        // Recuperar los detalles basicos de los registros
        Page<RecordDetailProjection> basicDetails = recordDetailRepository
                .findBasicDetailsByPersonId(peopleId, hospitalName, startDate, endDate, pageable);

        // Recuperar los archivos de los detalles basicos
        Map<UUID, List<FileBasicProjection>> filesMap = new HashMap<>();
        if (!basicDetails.isEmpty()) {
            // Recuperar los ids de los detalles basicos
            List<UUID> detailIds = basicDetails.getContent().stream()
                    .map(RecordDetailProjection::getId)
                    .toList();

            // Recuperar los archivos por los ids de los detalles basicos
            fileRepository.findFilesByRecordDetailIds(detailIds)
                    .forEach(file -> filesMap
                            .computeIfAbsent(file.getRecordDetailId(), k -> new ArrayList<>())
                            .add(file));
        }

        // Convertir los detalles basicos y los archivos a presentadores
        List<RecordDetailPresenter> presenters = basicDetails.stream()
                .map(projection -> toPresenter(projection, filesMap.getOrDefault(projection.getId(), List.of())))
                .toList();

        return new PageImpl<>(presenters, pageable, basicDetails.getTotalElements());
    }

    @Override
    public RecordDetailExtenseViewModel getRecordDetail(UUID recordDetailId) {
        RecordDetailExtenseProjection projection = recordDetailRepository.findExtenseDetailById(recordDetailId);

        // Recuperar los archivos por el id del detalle, pero usando el metodo findFilesByRecordDetailIds
        List<FileBasicProjection> files = fileRepository.findFilesByRecordDetailIds(List.of(recordDetailId));

        // Como solo se recupera un solo detalle, se puede obtener el primer elemento de la lista de archivos
        return toPresenter(projection, files);
    }

    private RecordDetailEntity createRecordDetail(
            RecordDetailCreateRequestDTO dto,
            UserEntity doctor,
            HospitalEntity hospital,
            RecordEntity record
    ) {
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

        return detail;
    }

    private Set<FileEntity> processFiles(Set<RecordDetailCreateRequestDTO.FileDTO> files, RecordDetailEntity detail) {
        List<CompletableFuture<FileEntity>> fileFutures = files.stream()
                .map(fileDto -> compressAndUploadService.compressAndUpload(fileDto.getFile())
                        .thenApply(compressFileDto -> createFileEntity(compressFileDto, fileDto, detail)))
                .toList();

        CompletableFuture.allOf(fileFutures.toArray(new CompletableFuture[0])).join();

        return fileFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());
    }

    private FileEntity createFileEntity(
            CompressFileDto compressFileDto,
            RecordDetailCreateRequestDTO.FileDTO fileDto,
            RecordDetailEntity detail
    ) {
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
    }
}