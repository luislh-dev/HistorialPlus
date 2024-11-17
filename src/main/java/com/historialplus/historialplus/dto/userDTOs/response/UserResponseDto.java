package com.historialplus.historialplus.dto.userDTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {

    private UUID id;
    private String name;
    private String email;
    private int stateId;
    private List<Integer> roleId;

    public UserResponseDto(UUID id, String name, String email, int stateId, List<Integer> roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.stateId = stateId;
        this.roleId = roleId;
    }
}