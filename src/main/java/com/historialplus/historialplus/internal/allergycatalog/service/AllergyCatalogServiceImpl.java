package com.historialplus.historialplus.internal.allergycatalog.service;

import com.historialplus.historialplus.common.constants.StateEnum;
import com.historialplus.historialplus.error.exceptions.ConflictException;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
import com.historialplus.historialplus.internal.allergycatalog.dto.request.AllergyCatalogRequestDto;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogDto;
import com.historialplus.historialplus.internal.allergycatalog.dto.response.AllergyCatalogPageResponseDTO;
import com.historialplus.historialplus.internal.allergycatalog.entities.AllergyCatalogEntity;
import com.historialplus.historialplus.internal.allergycatalog.mapper.AllergyCatalogMapper;
import com.historialplus.historialplus.internal.allergycatalog.mapper.Mapper;
import com.historialplus.historialplus.internal.allergycatalog.projection.AllergyCatalogProjection;
import com.historialplus.historialplus.internal.allergycatalog.repository.AllergyCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AllergyCatalogServiceImpl implements AllergyCatalogService{
    private static final String CODE_PREFIX = "ALG-";
    private static final String INITIAL_CODE = "ALG-0001";

    private final AllergyCatalogRepository allergyCatalogRepository;
    private final AllergyCatalogMapper allergyCatalogMapper;

    @Override
    @Transactional
    public AllergyCatalogDto create(AllergyCatalogRequestDto dto) {
        if (allergyCatalogRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ConflictException("Ya existe una alergia en el catálogo con el nombre: " + dto.getName());
        }

        AllergyCatalogEntity entity = allergyCatalogMapper.toEntity(dto);
        entity.setCode(generateNextCode());

        AllergyCatalogEntity savedEntity = allergyCatalogRepository.saveAndFlush(entity);
        return allergyCatalogMapper.toDto(savedEntity);
    }

    private String generateNextCode() {
        return allergyCatalogRepository.findFirstByOrderByCreatedAtDesc()
                .map(lastAllergy -> {
                    String lastCode = lastAllergy.getCode();
                    String numericPart = lastCode.substring(CODE_PREFIX.length());
                    int lastNumber = Integer.parseInt(numericPart);
                    return String.format("%s%04d", CODE_PREFIX, lastNumber + 1);
                })
                .orElse(INITIAL_CODE);
    }

    @Override
    @Transactional(readOnly = true)
    public AllergyCatalogDto findById(UUID id) {
        return allergyCatalogRepository.findById(id)
                .map(allergyCatalogMapper::toDto)
                .orElseThrow(() -> allergyNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyCatalogDto> findAllActive(Pageable pageable) {
        return allergyCatalogRepository.findAllByIsActiveTrue(pageable)
                .map(allergyCatalogMapper::toDto);
    }

    @Override
    @Transactional
    public AllergyCatalogDto update(UUID id, AllergyCatalogRequestDto dto) {
        AllergyCatalogEntity existingEntity = allergyCatalogRepository.findById(id)
                .orElseThrow(() -> allergyNotFoundException(id));

        if (allergyCatalogRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)) {
            throw new ConflictException("Ya existe otra alergia en el catálogo con el nombre: " + dto.getName());
        }

        allergyCatalogMapper.updateEntityFromDto(dto, existingEntity);
        AllergyCatalogEntity updatedEntity = allergyCatalogRepository.save(existingEntity);
        return allergyCatalogMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public AllergyCatalogDto deactivate(UUID id) {
        AllergyCatalogEntity entity = allergyCatalogRepository.findById(id)
                .orElseThrow(() -> allergyNotFoundException(id));

        entity.setIsActive(false);
        AllergyCatalogEntity savedEntity = allergyCatalogRepository.save(entity);
        return allergyCatalogMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public AllergyCatalogDto reactivate(UUID id) {
        AllergyCatalogEntity entity = allergyCatalogRepository.findById(id)
                .orElseThrow(() -> allergyNotFoundException(id));

        entity.setIsActive(true);
        AllergyCatalogEntity savedEntity = allergyCatalogRepository.save(entity);
        return allergyCatalogMapper.toDto(savedEntity);
    }

    @Override public Page<AllergyCatalogPageResponseDTO> findAllBy(String name, StateEnum status, Pageable pageable) {
        Boolean isActive = status == null ? null : status == StateEnum.ACTIVE;

        Page<AllergyCatalogProjection> response = allergyCatalogRepository.findAllWithProjection(name, isActive, pageable);
        return response.map(Mapper::toAllergyCatalogPageResponseDTO);
    }

    private NotFoundException allergyNotFoundException(UUID id) {
        return new NotFoundException("Alergia no encontrada en el catálogo con ID: " + id);
    }
}
