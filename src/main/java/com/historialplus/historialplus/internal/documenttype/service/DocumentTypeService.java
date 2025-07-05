package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeProjection> findAll();
}
