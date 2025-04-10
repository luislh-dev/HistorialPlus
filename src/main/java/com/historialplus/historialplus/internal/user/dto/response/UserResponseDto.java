package com.historialplus.historialplus.internal.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private int stateId;
    private List<Integer> roleId;
}