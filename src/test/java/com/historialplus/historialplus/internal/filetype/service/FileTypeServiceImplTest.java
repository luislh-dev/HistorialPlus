package com.historialplus.historialplus.internal.filetype.service;

import com.historialplus.historialplus.common.constants.FileTypeEnum;
import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.mapper.FileTypeMapper;
import com.historialplus.historialplus.internal.filetype.projection.FileTypeProjection;
import com.historialplus.historialplus.internal.filetype.repository.FileTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileTypeServiceImplTest {

	@Mock
	private FileTypeRepository repository;

	@Mock
	private FileTypeMapper mapper;

	@InjectMocks
	private FileTypeServiceImpl service;

	@Test
	void getAllTest() {
		FileTypeProjection projection = mock(FileTypeProjection.class);
		FileTypeDto dto = FileTypeDto.builder().id(1).name(FileTypeEnum.PRESCRIPTION.name()).build();

		when(repository.findAllProjectedBy()).thenReturn(List.of(projection));
		when(mapper.fileTypeProjectionToFileTypeDto(any())).thenReturn(dto);

		List<FileTypeDto> result = service.getAll();

		verify(repository).findAllProjectedBy();
		verify(mapper).fileTypeProjectionToFileTypeDto(projection);

		assertEquals(1, result.size());
		assertEquals(1, result.getFirst().getId());
		assertEquals(FileTypeEnum.PRESCRIPTION.name(), result.getFirst().getName());
	}

	@Test
	void getAllEmptyTest() {
		when(repository.findAllProjectedBy()).thenReturn(List.of());

		List<FileTypeDto> result = service.getAll();

		verify(repository).findAllProjectedBy();
		verifyNoInteractions(mapper);

		assertEquals(0, result.size());
	}

	@Test
	void getAllWithExceptionTest() {
		when(repository.findAllProjectedBy()).thenThrow(new RuntimeException("Database error"));

		List<FileTypeDto> result = service.getAll();

		verify(repository).findAllProjectedBy();
		verifyNoInteractions(mapper);

		assertEquals(0, result.size());
	}
}