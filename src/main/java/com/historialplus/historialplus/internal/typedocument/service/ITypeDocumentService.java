package com.historialplus.historialplus.internal.typedocument.service;

import com.historialplus.historialplus.internal.typedocument.projection.TypeDocumentProjection;

import java.util.List;

public interface ITypeDocumentService {
    List<TypeDocumentProjection> findAll();
}
