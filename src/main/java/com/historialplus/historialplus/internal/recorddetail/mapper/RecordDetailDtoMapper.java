package com.historialplus.historialplus.internal.recorddetail.mapper;

import com.historialplus.historialplus.internal.record.entites.RecordEntity;
import com.historialplus.historialplus.internal.recorddetail.dto.request.RecordDetailCreateDto;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import org.springframework.stereotype.Component;

@Component
public class RecordDetailDtoMapper {
    public static RecordDetailResponseDto toResponseDto(RecordDetailEntity detailEntity) {
        return new RecordDetailResponseDto(
                detailEntity.getId(),
                detailEntity.getReason(),
                detailEntity.getRecord().getId(),
                detailEntity.getState().getId()
        );
    }

    public static RecordDetailEntity toEntity(RecordDetailCreateDto detailDto, RecordEntity parentRecord) {
        RecordDetailEntity detailEntity = new RecordDetailEntity();
        detailEntity.setRecord(parentRecord);
        detailEntity.setReason(detailDto.getDescription());
        StateEntity state = new StateEntity();
        state.setId(detailDto.getStateId() != null ? detailDto.getStateId() : 1);
        detailEntity.setState(state);

        return detailEntity;
    }
}