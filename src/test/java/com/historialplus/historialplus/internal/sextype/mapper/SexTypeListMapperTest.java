package com.historialplus.historialplus.internal.sextype.mapper;

import com.historialplus.historialplus.common.enums.SexTypeEnum;
import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.entities.SexTypeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SexTypeListMapperTest {

	private final SexTypeListMapper mapper = Mappers.getMapper(SexTypeListMapper.class);

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