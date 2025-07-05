package com.historialplus.historialplus.internal.documenttype.repository;

import com.historialplus.historialplus.internal.documenttype.entities.DocumentTypeEntity;
import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Integer>  {
    List<DocumentTypeProjection> findAllByOrderByUpdatedAtDesc();
}
