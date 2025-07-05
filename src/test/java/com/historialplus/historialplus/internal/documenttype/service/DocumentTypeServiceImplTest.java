package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.common.constants.DocumentTypeEnum;
import com.historialplus.historialplus.internal.documenttype.projection.DocumentTypeProjection;
import com.historialplus.historialplus.internal.documenttype.repository.DocumentTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DocumentTypeServiceImplTest {

	@Mock
	private DocumentTypeRepository repository;

	@InjectMocks
	private DocumentTypeServiceImpl service;

	@Test
	void findAll() {
		DocumentTypeProjection projection = new DocumentTypeProjection() {
			public DocumentTypeEnum getId() {
				return DocumentTypeEnum.DNI;
			}

			public String getName() {
				return "DNI";
			}
		};
		DocumentTypeProjection projection2 = new DocumentTypeProjection() {
			public DocumentTypeEnum getId() {
				return DocumentTypeEnum.CE;
			}

			public String getName() {
				return "Carnet de extranjería";
			}
		};
		given(repository.findAllByOrderByUpdatedAtDesc()).willReturn(List.of(projection, projection2));

		List<DocumentTypeProjection> projections = service.findAll();

		assertNotNull(projections);
		assertEquals(2, projections.size());
	}
}