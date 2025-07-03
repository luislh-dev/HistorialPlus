package com.historialplus.historialplus.internal.people.dto.response;

import com.historialplus.historialplus.common.constants.DocumentTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MinimalPeopleResponseDto {
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String documentNumber;
    private DocumentTypeEnum documentType;
    private String phone;
    private boolean hasExternalSource;
    private String dataSource;
}
