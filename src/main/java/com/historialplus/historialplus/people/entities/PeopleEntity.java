package com.historialplus.historialplus.people.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.entities.RecordEntity;
import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.typesex.entities.SexTypeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "people")
@Getter
@Setter
public class PeopleEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "maternal_surname", nullable = false)
    private String maternalSurname;

    @Column(name = "paternal_surname", nullable = false)
    private String paternalSurname;

    private Date birthdate;

    @Column(name = "blood_type")
    private String bloodType;

    private String address;
    private String phone;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    private String nationality;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sex_type_id")
    private SexTypeEntity sexType;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_document_id")
    private TypeDocumentEntity typeDocument;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private RecordEntity record;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();
}
