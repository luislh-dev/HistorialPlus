package com.historialplus.historialplus.record.mapper;

import com.historialplus.historialplus.dto.recordDetailDTOs.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.record.dto.response.RecordResponseDto;
import com.historialplus.historialplus.record.entites.RecordEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecordDtoMapper {
    public static RecordResponseDto toResponseDto(RecordEntity recordEntity) {
        return new RecordResponseDto(
                recordEntity.getId(),
                recordEntity.getPerson().getName(),
                recordEntity.getPerson().getUsers().stream().map(user -> user.getHospital().getName()).findFirst().orElse(""),
                recordEntity.getVisits().stream()
                        .map(RecordDetailDtoMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
    }
}