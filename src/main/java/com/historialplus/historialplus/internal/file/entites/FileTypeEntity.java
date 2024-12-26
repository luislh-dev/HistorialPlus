package com.historialplus.historialplus.internal.file.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "file_type")
@Getter
@Setter
public class FileTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType name;

    @Getter
    public enum FileType {
        PRESCRIPTION("Recetas"),
        MEDICAL_REPORT("Informes médicos"),
        LAB_RESULT("Resultados de laboratorio"),
        XRAY("Radiografías"),
        SCAN("Otros tipos de imágenes médicas"),
        OTHER("Otros documentos");

        private final String displayName;

        FileType(String displayName) {
            this.displayName = displayName;
        }

    }

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}