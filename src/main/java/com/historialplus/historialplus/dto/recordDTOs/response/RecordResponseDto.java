package com.historialplus.historialplus.dto.recordDTOs.response;

import com.historialplus.historialplus.dto.recordDetailDTOs.response.RecordDetailResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RecordResponseDto {
    private UUID recordId;
    private String patientName;
    private String hospitalName;
    private List<RecordDetailResponseDto> details;

    public RecordResponseDto(UUID recordId, String patientName, String hospitalName, List<RecordDetailResponseDto> details) {
        this.recordId = recordId;
        this.patientName = patientName;
        this.hospitalName = hospitalName;
        this.details = details;
    }
}