package com.historialplus.historialplus.internal.allergy.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AllergyUpdateDto extends AllergyBaseDto {
    private Boolean isActive;
}
