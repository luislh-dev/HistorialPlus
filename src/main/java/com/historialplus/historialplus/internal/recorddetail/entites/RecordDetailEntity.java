package com.historialplus.historialplus.internal.recorddetail.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Lob
    @Column(name = "treatment", columnDefinition = "TEXT")
    private String treatment;

    @OneToMany(mappedBy = "recordDetail", cascade = CascadeType.ALL)
    private Set<FileEntity> files = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}