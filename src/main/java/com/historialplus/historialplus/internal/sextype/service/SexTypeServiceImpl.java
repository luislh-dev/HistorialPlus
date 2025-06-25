package com.historialplus.historialplus.internal.sextype.service;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.mapper.SexTypeListMapper;
import com.historialplus.historialplus.internal.sextype.repository.SexTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexTypeServiceImpl implements SexTypeService {

    private final SexTypeRepository repository;
	private final SexTypeListMapper mapper;

	public SexTypeServiceImpl(SexTypeRepository repository, SexTypeListMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
    public List<SexTypeResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::sexTypeEntityToSexTypeResponseDto)
                .toList();
    }
}
