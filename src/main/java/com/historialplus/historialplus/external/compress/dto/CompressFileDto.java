package com.historialplus.historialplus.external.compress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompressFileDto {
    private String previewUrl;
    private String objectKey;
    private Long sizeBytes;
    private String mimeType;
    private String name;
}
