package com.historialplus.historialplus.internal.allergycatalog.dto.response;

import com.historialplus.historialplus.common.constants.AllergyCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AllergyCatalogDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private AllergyCategory category;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
