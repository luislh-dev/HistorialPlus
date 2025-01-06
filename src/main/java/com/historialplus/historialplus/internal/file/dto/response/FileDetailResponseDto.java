package com.historialplus.historialplus.internal.file.dto.response;

public record FileDetailResponseDto(
        String Name,
        Long SizeInBytes,
        String Url,
        String TypeName
) {}
