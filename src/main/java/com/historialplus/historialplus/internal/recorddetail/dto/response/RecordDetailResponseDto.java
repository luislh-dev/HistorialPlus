package com.historialplus.historialplus.internal.recorddetail.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RecordDetailResponseDto {
    private UUID id;
    private String description;
    private UUID recordId;
    private Integer stateId;

}