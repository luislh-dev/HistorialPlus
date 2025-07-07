package com.historialplus.historialplus.internal.documenttype.repository;

import com.historialplus.historialplus.internal.documenttype.entities.DocumentTypeEntity;
import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;

import static com.historialplus.historialplus.common.enums.DocumentTypeEnum.CE;
import static com.historialplus.historialplus.common.enums.DocumentTypeEnum.DNI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class DocumentTypeRepositoryTest {

	@Autowired
	private DocumentTypeRepository repository;

	@Test
	void findAllByOrderByUpdatedAtDesc() {
		DocumentTypeEntity entity = DocumentTypeEntity.builder()
			.id(DNI)
			.name(DNI.getDisplayName())
			.updatedAt(Timestamp.valueOf("2025-10-01 10:00:00"))
			.build();

		DocumentTypeEntity entity2 = DocumentTypeEntity.builder()
			.id(CE)
			.name(CE.getDisplayName())
			.updatedAt(Timestamp.valueOf("2025-10-02 11:00:00"))
			.build();
		repository.save(entity);
		repository.save(entity2);

		List<DocumentTypeProjection> documents = repository.findAllByOrderByUpdatedAtDesc();

		assertThat(documents).isNotNull();
		assertEquals(2, documents.size());
		assertEquals(entity2.getName(), documents.getFirst().getName());
		assertEquals(entity.getName(), documents.getLast().getName());
	}
}