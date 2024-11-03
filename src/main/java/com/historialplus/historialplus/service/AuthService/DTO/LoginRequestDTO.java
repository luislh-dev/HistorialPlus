package com.historialplus.historialplus.service.AuthService.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String name;
    private String password;
}