package com.historialplus.historialplus.internal.file.dto.response;

import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;

public record FileDetailResponseDto(
        String Name,
        Long SizeInBytes,
        String Url,
        FileTypeEntity.FileType typeName
) {}
