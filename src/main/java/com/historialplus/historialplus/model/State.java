package com.historialplus.historialplus.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "states")
@Getter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
