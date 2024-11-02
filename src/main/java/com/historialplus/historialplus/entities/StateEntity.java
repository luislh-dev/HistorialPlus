package com.historialplus.historialplus.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "states")
@Getter
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
