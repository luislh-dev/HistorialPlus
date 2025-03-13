package com.historialplus.historialplus.internal.typesex.mapper;

import com.historialplus.historialplus.common.constants.SexTypeEnum;
import com.historialplus.historialplus.internal.typesex.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.typesex.entities.SexTypeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SexTypeListMapperTest {

	private final ISexTypeListMapper mapper = Mappers.getMapper(ISexTypeListMapper.class);

	@Test
	void sexTypeEntityToSexTypeResponseDto() {
		SexTypeEntity entity = SexTypeEntity.builder()
			.id(1)
			.name(SexTypeEnum.MALE)
			.build();

		SexTypeResponseDto dto = mapper.sexTypeEntityToSexTypeResponseDto(entity);

		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertNotNull(dto.getName());

		assertEquals(entity.getId(), dto.getId());
		assertEquals(entity.getName().getDisplayName(), dto.getName());
	}
}