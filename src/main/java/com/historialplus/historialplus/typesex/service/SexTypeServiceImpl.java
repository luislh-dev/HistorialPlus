package com.historialplus.historialplus.typesex.service;

import com.historialplus.historialplus.typesex.dto.SexTypeResponseDto;
import com.historialplus.historialplus.typesex.entities.SexTypeEntity;
import com.historialplus.historialplus.typesex.repository.SexTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SexTypeServiceImpl implements ISexTypeService {

    private final SexTypeRepository repository;

    public SexTypeServiceImpl(SexTypeRepository sexTypeRepository) {
        this.repository = sexTypeRepository;
    }


    @Override
    public List<SexTypeResponseDto> findAll() {
        List<SexTypeEntity> entities = repository.findAll();

        List<SexTypeResponseDto> response = new ArrayList<>();

        for (SexTypeEntity sexTypeEntity : entities) {
            SexTypeResponseDto sexType = new SexTypeResponseDto();
            sexType.setId(sexTypeEntity.getId());
            sexType.setName(sexTypeEntity.getName());
            response.add(sexType);
        }

        return response;
    }
}
