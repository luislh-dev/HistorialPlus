package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.dto.DocumentTypeDTO;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeDTO> findAll();
}
