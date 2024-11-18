package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileEntity {
    @Id
    private byte[] id;

    @ManyToOne
    @JoinColumn(name = "record_detail_id", nullable = false)
    private RecordDetailEntity recordDetail;

//    @Column(name = "file_type_id", nullable = false)
//    private int fileTypeId;
    @Setter
    @ManyToOne
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileTypeEntity fileType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

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