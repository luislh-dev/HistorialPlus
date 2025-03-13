package com.historialplus.historialplus.internal.typesex.service;

import com.historialplus.historialplus.internal.typesex.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.typesex.mapper.ISexTypeListMapper;
import com.historialplus.historialplus.internal.typesex.repository.SexTypeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexTypeServiceImpl implements ISexTypeService {

    private final SexTypeRepository repository;
	private final ISexTypeListMapper mapper;

	public SexTypeServiceImpl(SexTypeRepository repository, @Qualifier("ISexTypeListMapper") ISexTypeListMapper mapper) {
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
