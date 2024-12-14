package com.historialplus.historialplus.dto.userDTOs.request;

import com.historialplus.historialplus.dto.BasePersonCreationDto;
import lombok.Getter;
import lombok.Setter;

// DTO para crear management (usado por ADMIN)
@Getter
@Setter
public class ManagementCreationDto extends BasePersonCreationDto {
    private Integer hospitalId;  // Requerido para management
}