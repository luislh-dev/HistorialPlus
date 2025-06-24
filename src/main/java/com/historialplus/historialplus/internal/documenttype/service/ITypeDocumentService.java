package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.projection.TypeDocumentProjection;

import java.util.List;

public interface ITypeDocumentService {
    List<TypeDocumentProjection> findAll();
}
