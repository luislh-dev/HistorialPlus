package com.historialplus.historialplus.internal.documenttype.controller;

import com.historialplus.historialplus.common.enums.DocumentTypeEnum;
import com.historialplus.historialplus.internal.documenttype.dto.DocumentTypeDTO;
import com.historialplus.historialplus.internal.documenttype.service.DocumentTypeService;
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

@WebMvcTest(controllers = DocumentTypeController.class)
class DocumentTypeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DocumentTypeService service;

	@Test
	@WithMockUser
	void findAll() throws Exception {
		List<DocumentTypeDTO> documents = new ArrayList<>(List.of());
		documents.add(DocumentTypeDTO.builder()
				.id(DocumentTypeEnum.DNI.name())
				.name(DocumentTypeEnum.DNI.getDisplayName())
				.build()
		);
		documents.add(DocumentTypeDTO.builder()
				.id(DocumentTypeEnum.CE.name())
				.name(DocumentTypeEnum.CE.getDisplayName())
				.build()
		);
		
		given(service.findAll()).willReturn(documents);

		ResultActions response = mvc.perform(get("/api/documentType"));

		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.size()").value(documents.size()));
	}

}