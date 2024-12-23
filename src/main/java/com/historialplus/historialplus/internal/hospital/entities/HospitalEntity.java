package com.historialplus.historialplus.internal.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "hospital")
@Getter
@Setter
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 11)
    private String ruc;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    @OneToMany(mappedBy = "hospital")
    private List<RecordDetailEntity> visits;

    @OneToMany(mappedBy = "hospital")
    private List<UserEntity> users;

}
