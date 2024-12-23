package com.historialplus.historialplus.internal.file.mapper;

import com.historialplus.historialplus.internal.file.dto.request.FilesCreateDto;
import com.historialplus.historialplus.internal.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.internal.file.entites.FileEntity;
import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import org.springframework.stereotype.Component;

@Component
public class FilesDtoMapper {
    public static FileEntity toEntity(FilesCreateDto fileDto, RecordDetailEntity parentDetail) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileDto.getName());
        fileEntity.setUrl(fileDto.getUrl());
        fileEntity.setRecordDetail(parentDetail);

        FileTypeEntity fileType = new FileTypeEntity();
        fileType.setId(fileDto.getFileTypeId());
        fileEntity.setFileType(fileType);

        return fileEntity;
    }

    public static FilesResponseDto toResponseDto(FileEntity fileEntity) {
        return new FilesResponseDto(
                fileEntity.getId().toString().getBytes(),
                fileEntity.getName(),
                fileEntity.getUrl(),
                fileEntity.getFileType().getId()
        );
    }

    public static FilesCreateDto toCreateDto(FileEntity fileEntity) {
        return new FilesCreateDto(
                fileEntity.getFileType().getId(),
                fileEntity.getName(),
                fileEntity.getUrl()
        );
    }
}