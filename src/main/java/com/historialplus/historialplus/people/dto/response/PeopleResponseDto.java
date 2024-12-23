package com.historialplus.historialplus.people.dto.response;

import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Getter
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

    public PeopleResponseDto(
            UUID id,
            String name,
            String paternalSurname,
            String maternalSurname,
            Date birthdate,
            String documentNumber,
            String bloodType,
            String address,
            String phone,
            String nationality,
            String sexType,
            String typeDocument
    ) {
        this.id = id;
        this.name = name;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.birthdate = birthdate;
        this.documentNumber = documentNumber;
        this.bloodType = bloodType;
        this.address = address;
        this.phone = phone;
        this.nationality = nationality;
        this.sexType = sexType;
        this.typeDocument = typeDocument;
    }
}
