package com.historialplus.historialplus.typedocument.service;

import com.historialplus.historialplus.typedocument.projection.TypeDocumentProjection;
import com.historialplus.historialplus.typedocument.repository.TypeDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeDocumentServiceImpl implements ITypeDocumentService {

    private final TypeDocumentRepository repository;

    public TypeDocumentServiceImpl(TypeDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TypeDocumentProjection> findAll() {
        return repository.findAllByOrderByUpdatedAtDesc();
    }
}
