package com.historialplus.historialplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePersonCreationDto {
    private String dni;
    private String name;
    private String maternalSurname;
    private String paternalSurname;
    private String phone;
    private String email;
    private String password;
}