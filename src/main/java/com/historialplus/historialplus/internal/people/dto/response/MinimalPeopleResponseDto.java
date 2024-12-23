package com.historialplus.historialplus.internal.people.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimalPeopleResponseDto {
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String documentNumber;
    private String documentType;
    private String phone; // Es un campo opcional
    private boolean hasExternalSource; // Si es true, significa que la persona fue creada por la reniec
    private String dataSource; // Atributo que indica la fuente de la que se obtuvo la información

    public MinimalPeopleResponseDto(String name, String fatherLastName, String motherLastName, String documentNumber, String documentType, String phone, boolean hasExternalSource, String dataSource) {
        this.name = name;
        this.fatherLastName = fatherLastName;
        this.motherLastName = motherLastName;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.phone = phone;
        this.hasExternalSource = hasExternalSource;
        this.dataSource = dataSource;
    }

}
