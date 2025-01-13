package com.historialplus.historialplus.internal.file.repository;

import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.projection.FileBasicProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    @Query("""
                SELECT f.recordDetail.id as recordDetailId,f.name as name, f.sizeInBytes as sizeInBytes,
                       f.url as url, f.mimeType as mimeType,
                       f.objectKey as objectKey,
                       ft.name as typeName
                FROM FileEntity f
                JOIN f.fileType ft
                WHERE f.recordDetail.id IN :recordDetailIds
            """)
    List<FileBasicProjection> findFilesByRecordDetailIds(@Param("recordDetailIds") List<UUID> ids);
}