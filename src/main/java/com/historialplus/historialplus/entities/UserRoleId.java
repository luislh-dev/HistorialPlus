package com.historialplus.historialplus.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class UserRoleId implements Serializable {

    private UUID userId; // Correspondiente a user_id en la tabla

    private Integer roleId; // Correspondiente a role_id en la tabla

    public UserRoleId() {}

    public UserRoleId(UUID userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
