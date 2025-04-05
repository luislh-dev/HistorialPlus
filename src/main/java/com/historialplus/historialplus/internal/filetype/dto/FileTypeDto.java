package com.historialplus.historialplus.internal.filetype.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileTypeDto {
    private Integer id;
    private String name;

    public FileTypeDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
