package com.historialplus.historialplus.internal.sextype.service;

import com.historialplus.historialplus.internal.sextype.dto.SexTypeResponseDto;
import com.historialplus.historialplus.internal.sextype.mapper.ISexTypeListMapper;
import com.historialplus.historialplus.internal.sextype.repository.SexTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexTypeServiceImpl implements ISexTypeService {

    private final SexTypeRepository repository;
	private final ISexTypeListMapper mapper;

	public SexTypeServiceImpl(SexTypeRepository repository, ISexTypeListMapper mapper) {
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
