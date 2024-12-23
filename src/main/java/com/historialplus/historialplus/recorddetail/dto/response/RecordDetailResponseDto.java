package com.historialplus.historialplus.recorddetail.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecordDetailResponseDto {
    private UUID id;
    private String description;
    private UUID recordId;
    private Integer stateId;

    public RecordDetailResponseDto(UUID id, String description, UUID recordId, Integer stateId) {
        this.id = id;
        this.description = description;
        this.recordId = recordId;
        this.stateId = stateId;
    }
}