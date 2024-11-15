package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "record")
public class RecordEntity {
    @Id
    private byte[] id;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private PeopleEntity person;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;

    @OneToMany(mappedBy = "record")
    private List<RecordDetailEntity> recordDetails;

    public RecordEntity() {}
}
