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
@Table(name = "record_detail")
public class RecordDetailEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private RecordEntity record;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "state_id", nullable = false)
    private Integer stateId = 1; // Default to active

    @OneToMany(mappedBy = "recordDetail", cascade = CascadeType.ALL)
    private List<FileEntity> files;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void setState(StateEntity state) {
        this.stateId = state.getId();
    }

    public StateEntity getState() {
        StateEntity state = new StateEntity();
        state.setId(this.stateId);
        return state;
    }
}