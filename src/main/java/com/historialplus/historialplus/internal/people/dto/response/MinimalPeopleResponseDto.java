package com.historialplus.historialplus.internal.people.dto.response;

import lombok.Data;

@Data
public class MinimalPeopleResponseDto {
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String documentNumber;
    private String documentType;
    private String phone;
    private boolean hasExternalSource;
    private String dataSource;
}
