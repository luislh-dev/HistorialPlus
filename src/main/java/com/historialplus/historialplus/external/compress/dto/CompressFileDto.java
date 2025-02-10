package com.historialplus.historialplus.external.compress.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompressFileDto {
    private String objectKey;
    private MultipartFile file;
}
