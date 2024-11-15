package com.historialplus.historialplus.dto.peopleDTOs.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.sql.Date;

@Getter
public class PeopleCreateDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String paternalSurname;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
    private String maternalSurname;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private Date birthdate;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, max = 20, message = "El número de documento debe tener entre 8 y 20 caracteres")
    private String documentNumber;

    private String bloodType;

    private String address;

    private String phone;

    private String nationality;

    @NotNull(message = "El tipo de sexo es obligatorio")
    private Integer sexTypeId;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer typeDocumentId;

    public PeopleCreateDto(
            String name,
            String paternalSurname,
            String maternalSurname,
            Date birthdate,
            String documentNumber,
            String bloodType,
            String address,
            String phone,
            String nationality,
            Integer sexTypeId,
            Integer typeDocumentId
    ) {
        this.name = name;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.birthdate = birthdate;
        this.documentNumber = documentNumber;
        this.bloodType = bloodType;
        this.address = address;
        this.phone = phone;
        this.nationality = nationality;
        this.sexTypeId = sexTypeId;
        this.typeDocumentId = typeDocumentId;
    }
}
