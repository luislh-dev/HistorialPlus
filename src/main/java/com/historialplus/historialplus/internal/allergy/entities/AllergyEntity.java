package com.historialplus.historialplus.internal.allergy.entities;

import com.historialplus.historialplus.common.constants.SeverityLevel;
import com.historialplus.historialplus.internal.people.entities.PeopleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "allergies")
@Entity
@Builder
public class AllergyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergy_id")
    private Integer allergyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false)
    private PeopleEntity people;

    @Column(name = "allergen_substance", nullable = false, length = 100)
    private String allergenSubstance;

    @Column(name = "reaction", columnDefinition = "TEXT")
    private String reaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20)
    private SeverityLevel severity;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "recorded_date")
    private LocalDate recordedDate;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
