package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.projection.TypeDocumentProjection;
import com.historialplus.historialplus.internal.documenttype.repository.TypeDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeDocumentServiceImpl implements ITypeDocumentService {

    private final TypeDocumentRepository repository;

    @Override
    public List<TypeDocumentProjection> findAll() {
        return repository.findAllByOrderByUpdatedAtDesc();
    }
}
