package com.historialplus.historialplus.typedocument.repository;

import com.historialplus.historialplus.typedocument.entities.TypeDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocumentEntity, Integer>  {
}
