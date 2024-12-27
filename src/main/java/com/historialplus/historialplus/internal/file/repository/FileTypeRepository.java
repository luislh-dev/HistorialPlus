package com.historialplus.historialplus.internal.file.repository;

import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;
import com.historialplus.historialplus.internal.file.projection.FileTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileTypeRepository extends JpaRepository<FileTypeEntity, Integer> {
    @Query("SELECT f.id AS id, f.name as name FROM FileTypeEntity f")
    List<FileTypeProjection> findAllProjectedBy();
}
