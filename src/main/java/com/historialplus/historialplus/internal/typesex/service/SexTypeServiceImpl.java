package com.historialplus.historialplus.internal.typesex.service;

import com.historialplus.historialplus.internal.typesex.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;
import com.historialplus.historialplus.internal.typesex.repository.SexTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SexTypeServiceImpl implements ISexTypeService {

    private final SexTypeRepository repository;

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
