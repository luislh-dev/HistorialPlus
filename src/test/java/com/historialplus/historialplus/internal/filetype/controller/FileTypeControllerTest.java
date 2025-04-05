package com.historialplus.historialplus.internal.filetype.controller;

import com.historialplus.historialplus.common.constants.FileTypeEnum;
import com.historialplus.historialplus.internal.filetype.dto.FileTypeDto;
import com.historialplus.historialplus.internal.filetype.service.IFileTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileTypeController.class)
class FileTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IFileTypeService fileTypeService;

	@Test
	@WithMockUser
	void getAllFileTypesTest() throws Exception {
		List<FileTypeDto> fileTypeDtos = List.of(
			new FileTypeDto(1, FileTypeEnum.XRAY.getDisplayName()),
			new FileTypeDto(2, FileTypeEnum.MEDICAL_REPORT.getDisplayName()),
			new FileTypeDto(3, FileTypeEnum.LAB_RESULT.getDisplayName())
		);
		when(fileTypeService.getAll()).thenReturn(fileTypeDtos);

		ResultActions resultActions = mockMvc.perform(get("/api/file-type"));

		resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].name").value(FileTypeEnum.XRAY.getDisplayName()))
			.andExpect(jsonPath("$[1].id").value(2))
			.andExpect(jsonPath("$[1].name").value(FileTypeEnum.MEDICAL_REPORT.getDisplayName()))
			.andExpect(jsonPath("$[2].id").value(3))
			.andExpect(jsonPath("$[2].name").value(FileTypeEnum.LAB_RESULT.getDisplayName()))
			.andExpect(jsonPath("$.size()").value(fileTypeDtos.size()));
	}
}