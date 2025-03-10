package com.historialplus.historialplus.internal.typedocument.controller;

import com.historialplus.historialplus.internal.typedocument.projection.TypeDocumentProjection;
import com.historialplus.historialplus.internal.typedocument.service.ITypeDocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TypeDocumentController.class)
class TypeDocumentControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ITypeDocumentService service;

	@Test
	@WithMockUser
	void findAll() throws Exception {
		List<TypeDocumentProjection> documents = new ArrayList<>();
		documents.add( new TypeDocumentProjection() {
			public Integer getId() {
				return 1;
			}

			public String getName() {
				return "DNI";
			}
		});
		documents.add(new TypeDocumentProjection() {
			public Integer getId() {
				return 2;
			}

			public String getName() {
				return "Carnet de extranjería";
			}
		});
		given(service.findAll()).willReturn(documents);

		ResultActions response = mvc.perform(get("/api/documentType"));

		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.size()").value(documents.size()));
	}

}