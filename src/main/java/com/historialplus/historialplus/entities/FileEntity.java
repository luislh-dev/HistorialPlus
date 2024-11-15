package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    private byte[] id;

    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "record_detail_id")
    private RecordDetailEntity recordDetail;

    @ManyToOne
    @JoinColumn(name = "file_type_id")
    private FileTypeEntity fileType;

    public FileEntity() {}
}
