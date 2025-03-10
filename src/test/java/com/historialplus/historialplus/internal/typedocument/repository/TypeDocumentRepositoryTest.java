package com.historialplus.historialplus.internal.typedocument.repository;

import com.historialplus.historialplus.internal.typedocument.entities.TypeDocumentEntity;
import com.historialplus.historialplus.internal.typedocument.projection.TypeDocumentProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;

import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.CE;
import static com.historialplus.historialplus.common.constants.DocumentTypeEnum.DNI;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TypeDocumentRepositoryTest {

	@Autowired
	private TypeDocumentRepository repository;

	@Test
	void findAllByOrderByUpdatedAtDesc() {
		TypeDocumentEntity entity = TypeDocumentEntity.builder()
			.name(DNI)
			.updatedAt(Timestamp.valueOf("2025-10-01 10:00:00"))
			.build();

		TypeDocumentEntity entity2 = TypeDocumentEntity.builder()
			.name(CE)
			.updatedAt(Timestamp.valueOf("2025-10-02 11:00:00"))
			.build();
		repository.save(entity);
		repository.save(entity2);

		List<TypeDocumentProjection> documents = repository.findAllByOrderByUpdatedAtDesc();

		assertThat(documents).isNotNull();
		assertThat(documents.size()).isEqualTo(2);
		assertThat(documents.get(0).getName()).isEqualTo(entity2.getName().name());
		assertThat(documents.get(1).getName()).isEqualTo(entity.getName().name());
	}
}