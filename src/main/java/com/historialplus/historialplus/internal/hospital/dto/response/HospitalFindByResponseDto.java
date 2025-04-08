package com.historialplus.historialplus.internal.hospital.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalFindByResponseDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String ruc;
    private Integer stateId;
}
