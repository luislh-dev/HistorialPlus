package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "file_type")
public class FileTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;
}
