package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "record_detail")
public class RecordDetailEntity {
    @Id
    private byte[] id;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private RecordEntity record;

    private String description;

    @OneToMany(mappedBy = "recordDetail")
    private List<FileEntity> files;

    public RecordDetailEntity() {}
}
