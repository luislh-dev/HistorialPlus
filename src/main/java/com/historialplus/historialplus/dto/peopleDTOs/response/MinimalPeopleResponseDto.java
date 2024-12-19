package com.historialplus.historialplus.dto.peopleDTOs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimalPeopleResponseDto {
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String phone; // Es un campo opcional
    private boolean hasExternalSource; // Si es true, significa que la persona fue creada por la reniec
}
