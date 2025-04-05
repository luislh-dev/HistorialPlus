package com.historialplus.historialplus.internal.filetype.mapper;

import com.historialplus.historialplus.common.constants.FileTypeEnum;
import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.projection.FileTypeProjection;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileTypeMapperTest {

	private final FileTypeMapper fileTypeMapper = Mappers.getMapper(FileTypeMapper.class);

	@Test
	void fileTypeProjectionToFileTypeDtoTest() {
		FileTypeProjection fileTypeProjection = new FileTypeProjection() {
			@Override public Integer getId() {
				return 1;
			}

			@Override public FileTypeEnum getName() {
				return FileTypeEnum.LAB_RESULT;
			}
		};

		FileTypeDto fileTypeDto = fileTypeMapper.fileTypeProjectionToFileTypeDto(fileTypeProjection);

		assertNotNull(fileTypeDto);
		assertNotNull(fileTypeDto.getId());
		assertNotNull(fileTypeDto.getName());

		assertEquals(fileTypeProjection.getId(), fileTypeDto.getId());
		assertEquals(fileTypeProjection.getName().getDisplayName(), fileTypeDto.getName());
	}
}