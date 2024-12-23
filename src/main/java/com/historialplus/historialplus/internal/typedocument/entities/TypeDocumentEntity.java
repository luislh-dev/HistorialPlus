package com.historialplus.historialplus.internal.typedocument.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_document")
@Getter
@Setter
public class TypeDocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "typeDocument", fetch = FetchType.LAZY)
    private List<PeopleEntity> people = new ArrayList<>();
}
