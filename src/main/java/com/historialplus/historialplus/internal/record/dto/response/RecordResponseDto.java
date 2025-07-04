package com.historialplus.historialplus.internal.record.dto.response;

import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
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