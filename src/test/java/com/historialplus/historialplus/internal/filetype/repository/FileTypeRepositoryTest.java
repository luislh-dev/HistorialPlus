package com.historialplus.historialplus.internal.filetype.repository;

import com.historialplus.historialplus.common.enums.FileTypeEnum;
import com.historialplus.historialplus.internal.filetype.entities.FileTypeEntity;
import com.historialplus.historialplus.internal.filetype.projection.FileTypeProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class FileTypeRepositoryTest {

	@Autowired
	private FileTypeRepository repository;

	@Test
	void findAllProjectedByTest() {
		FileTypeEntity fileTypeEntity = new FileTypeEntity();
		fileTypeEntity.setId(1);
		fileTypeEntity.setName(FileTypeEnum.PRESCRIPTION);

		repository.save(fileTypeEntity);

		List<FileTypeProjection> projections = repository.findAllProjectedBy();

		assertNotNull(projections);
		assertNotNull(projections.getFirst().getId());
		assertNotNull(projections.getFirst().getName());

		assertEquals(1, projections.size());
		assertEquals(projections.getFirst().getId(), fileTypeEntity.getId());
		assertEquals(projections.getFirst().getName(), fileTypeEntity.getName());
	}
}