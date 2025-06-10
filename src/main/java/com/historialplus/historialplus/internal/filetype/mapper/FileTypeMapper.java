package com.historialplus.historialplus.internal.filetype.mapper;

import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.projection.FileTypeProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileTypeMapper {
	@Mapping(source = "name.displayName", target = "name")
	FileTypeDto fileTypeProjectionToFileTypeDto(FileTypeProjection fileTypeProjection);
}
