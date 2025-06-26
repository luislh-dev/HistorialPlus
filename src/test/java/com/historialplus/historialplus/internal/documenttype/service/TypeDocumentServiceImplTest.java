package com.historialplus.historialplus.internal.documenttype.service;

import com.historialplus.historialplus.internal.documenttype.projection.TypeDocumentProjection;
import com.historialplus.historialplus.internal.documenttype.repository.TypeDocumentRepository;
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
class TypeDocumentServiceImplTest {

	@Mock
	private TypeDocumentRepository repository;

	@InjectMocks
	private TypeDocumentServiceImpl service;

	@Test
	void findAll() {
		TypeDocumentProjection projection = new TypeDocumentProjection() {
			public Integer getId() {
				return 1;
			}

			public String getName() {
				return "DNI";
			}
		};
		TypeDocumentProjection projection2 = new TypeDocumentProjection() {
			public Integer getId() {
				return 2;
			}

			public String getName() {
				return "Carnet de extranjería";
			}
		};
		given(repository.findAllByOrderByUpdatedAtDesc()).willReturn(List.of(projection, projection2));

		List<TypeDocumentProjection> projections = service.findAll();

		assertNotNull(projections);
		assertEquals(2, projections.size());
	}
}