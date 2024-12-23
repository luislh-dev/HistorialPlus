package com.historialplus.historialplus.people.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
}
