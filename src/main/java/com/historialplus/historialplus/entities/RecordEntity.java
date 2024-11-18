package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "record")
public class RecordEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "people_id", nullable = false)
    private PeopleEntity person;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordDetailEntity> recordDetails;

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