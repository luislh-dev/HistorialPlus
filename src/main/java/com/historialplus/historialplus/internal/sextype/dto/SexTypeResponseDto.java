package com.historialplus.historialplus.internal.sextype.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SexTypeResponseDto {
    private Integer id;
    private String name;
}
