package com.historialplus.historialplus.internal.people.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;
import com.historialplus.historialplus.internal.user.entites.UserEntity;
import jakarta.persistence.*;
import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();
}
