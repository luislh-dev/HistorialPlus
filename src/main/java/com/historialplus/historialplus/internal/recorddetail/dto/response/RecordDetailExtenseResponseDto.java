package com.historialplus.historialplus.internal.recorddetail.dto.response;

import com.historialplus.historialplus.internal.file.dto.response.FileDetailResponseDto;

import java.util.Set;
import java.util.UUID;


public record RecordDetailExtenseResponseDto(
        UUID id,
        String reason,
        String hospitalName,
        String doctorFullName,
        Set<FileDetailResponseDto> files
) {}
