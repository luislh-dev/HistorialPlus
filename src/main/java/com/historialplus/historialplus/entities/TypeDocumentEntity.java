package com.historialplus.historialplus.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "typeDocument", fetch = FetchType.LAZY)
    private List<PeopleEntity> people = new ArrayList<>();
}
