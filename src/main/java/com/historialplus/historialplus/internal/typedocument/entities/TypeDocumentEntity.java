package com.historialplus.historialplus.internal.typedocument.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.historialplus.historialplus.common.constants.DocumentTypeEnum;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum name;

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
