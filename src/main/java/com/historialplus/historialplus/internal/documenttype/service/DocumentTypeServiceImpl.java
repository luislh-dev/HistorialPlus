package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.dto.DocumentTypeDTO;
import com.historialplus.historialplus.internal.documenttype.mapper.Mapper;
import com.historialplus.historialplus.internal.documenttype.repository.DocumentTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    @Override
    public List<DocumentTypeDTO> findAll() {
        return repository.findAllByOrderByUpdatedAtDesc().stream().map(Mapper::toDTO).toList();
    }
}
