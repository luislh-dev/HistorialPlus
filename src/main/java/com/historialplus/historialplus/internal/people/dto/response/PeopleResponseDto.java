package com.historialplus.historialplus.internal.people.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Builder
public class PeopleResponseDto {
    private final UUID id;
    private final String name;
    private final String paternalSurname;
    private final String maternalSurname;
    private final Date birthdate;
    private final String documentNumber;
    private final String bloodType;
    private final String address;
    private final String phone;
    private final String nationality;
    private final String sexType;
    private final String typeDocument;
}
