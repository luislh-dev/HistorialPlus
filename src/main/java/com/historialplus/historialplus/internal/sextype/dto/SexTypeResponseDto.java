package com.historialplus.historialplus.internal.sextype.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SexTypeResponseDto {
    private Integer id;
    private String name;
}
