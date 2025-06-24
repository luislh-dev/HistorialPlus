package com.historialplus.historialplus.internal.sextype.service;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.entities.SexTypeEntity;
import com.historialplus.historialplus.internal.sextype.mapper.ISexTypeListMapper;
import com.historialplus.historialplus.internal.sextype.repository.SexTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.historialplus.historialplus.common.constants.SexTypeEnum.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SexTypeServiceImplTest {

	@Mock
	private SexTypeRepository repository;

	@Mock
	private ISexTypeListMapper mapper;

	@InjectMocks
	private SexTypeServiceImpl service;

	@Test
	void findAllTest() {
		List<SexTypeEntity> entities = new ArrayList<>();
		SexTypeEntity entity = SexTypeEntity.builder().id(1).name(MALE).build();
		entities.add(entity);

		SexTypeResponseDto dto = SexTypeResponseDto.builder().id(1).name(MALE.getDisplayName()).build();

		when(repository.findAll()).thenReturn(entities);
		when(mapper.sexTypeEntityToSexTypeResponseDto(entity)).thenReturn(dto);

		List<SexTypeResponseDto> dtos = service.findAll();

		assertNotNull(dtos);
		assertEquals(1, dtos.size());
		assertEquals(1, dtos.getFirst().getId());
		assertEquals(MALE.getDisplayName(), dtos.getFirst().getName());
	}

}