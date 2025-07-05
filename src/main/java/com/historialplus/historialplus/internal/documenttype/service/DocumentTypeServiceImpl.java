package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;
import com.historialplus.historialplus.internal.documenttype.repository.DocumentTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    @Override
    public List<DocumentTypeProjection> findAll() {
        return repository.findAllByOrderByUpdatedAtDesc();
    }
}
