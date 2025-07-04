package com.historialplus.historialplus.internal.file.dto.response;

import lombok.Data;

@Data
public class FilesResponseDto {
    private byte[] id;
    private String name;
    private String url;
    private int fileTypeId;

    public FilesResponseDto(byte[] id, String name, String url, int fileTypeId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.fileTypeId = fileTypeId;
    }
}