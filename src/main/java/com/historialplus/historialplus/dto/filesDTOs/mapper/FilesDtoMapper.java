package com.historialplus.historialplus.dto.filesDTOs.mapper;

import com.historialplus.historialplus.dto.filesDTOs.request.FilesCreateDto;
import com.historialplus.historialplus.dto.filesDTOs.response.FilesResponseDto;
import com.historialplus.historialplus.entities.FileEntity;
import com.historialplus.historialplus.entities.FileTypeEntity;
import com.historialplus.historialplus.entities.RecordDetailEntity;
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