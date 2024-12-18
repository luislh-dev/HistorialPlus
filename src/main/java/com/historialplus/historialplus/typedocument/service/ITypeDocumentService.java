package com.historialplus.historialplus.typedocument.service;

import com.historialplus.historialplus.typedocument.projection.TypeDocumentProjection;

import java.util.List;

public interface ITypeDocumentService {
    List<TypeDocumentProjection> findAll();
}
