package com.historialplus.historialplus.internal.record.entites;

import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "people_id", nullable = false, unique = true)
    private PeopleEntity person;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordDetailEntity> visits;
}