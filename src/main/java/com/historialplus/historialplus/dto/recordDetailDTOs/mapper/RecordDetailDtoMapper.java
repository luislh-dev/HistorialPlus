package com.historialplus.historialplus.dto.recordDetailDTOs.mapper;

import com.historialplus.historialplus.dto.filesDTOs.mapper.FilesDtoMapper;
import com.historialplus.historialplus.dto.recordDetailDTOs.request.RecordDetailCreateDto;
import com.historialplus.historialplus.dto.recordDetailDTOs.response.RecordDetailResponseDto;
import com.historialplus.historialplus.entities.RecordDetailEntity;
import com.historialplus.historialplus.entities.RecordEntity;
import com.historialplus.historialplus.entities.StateEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecordDetailDtoMapper {
    public static RecordDetailResponseDto toResponseDto(RecordDetailEntity detailEntity) {
        return new RecordDetailResponseDto(
                detailEntity.getId(),
                detailEntity.getDescription(),
                detailEntity.getRecord().getId(),
                detailEntity.getState().getId()
        );
    }

    public static RecordDetailCreateDto toCreateDto(RecordDetailEntity detailEntity) {
        return new RecordDetailCreateDto(
                detailEntity.getDescription(),
                detailEntity.getState().getId(),
                detailEntity.getRecord().getId(),
                detailEntity.getFiles().stream()
                        .map(FilesDtoMapper::toCreateDto)
                        .collect(Collectors.toList())
        );
    }

    public static RecordDetailEntity toEntity(RecordDetailCreateDto detailDto, RecordEntity parentRecord) {
        RecordDetailEntity detailEntity = new RecordDetailEntity();
        detailEntity.setRecord(parentRecord);
        detailEntity.setDescription(detailDto.getDescription());

        StateEntity state = new StateEntity();
        state.setId(detailDto.getStateId() != null ? detailDto.getStateId() : 1);
        detailEntity.setState(state);

        detailEntity.setFiles(
                detailDto.getFiles().stream()
                        .map(fileDto -> FilesDtoMapper.toEntity(fileDto, detailEntity))
                        .collect(Collectors.toList())
        );

        return detailEntity;
    }
}