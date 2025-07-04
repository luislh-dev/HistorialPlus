package com.historialplus.historialplus.internal.file.entites;

import com.historialplus.historialplus.internal.filetype.entities.FileTypeEntity;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
public class FileEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "record_detail_id", nullable = false)
    private RecordDetailEntity recordDetail;

    @ManyToOne
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileTypeEntity fileType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "object_key")
    private String objectKey;

    @Column(name = "size_in_bytes")
    private Long sizeInBytes;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

}