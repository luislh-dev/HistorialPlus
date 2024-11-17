package com.historialplus.historialplus.dto.userDTOs.request;

import com.historialplus.historialplus.validators.state.ValidStateId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserUpdateDto {

    @Size(min = 3, max = 50, message = "Nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @Email(message = "Email no es válido")
    private String email;

    @ValidStateId
    private Integer stateId;

    private List<Integer> roleIds;

}