package com.historialplus.historialplus.internal.file.repository;

import com.historialplus.historialplus.internal.file.entites.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
}