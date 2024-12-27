package com.historialplus.historialplus.internal.file.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileTypeDto {
    private Integer id;
    private String name;

    public FileTypeDto(Integer id, String displayName) {
        this.id = id;
        this.name = displayName;
    }
}
