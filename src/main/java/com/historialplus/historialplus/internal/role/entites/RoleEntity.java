package com.historialplus.historialplus.internal.role.entites;


import com.historialplus.historialplus.common.constants.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

}
