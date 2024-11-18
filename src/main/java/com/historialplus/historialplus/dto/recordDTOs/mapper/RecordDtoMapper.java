package com.historialplus.historialplus.dto.recordDTOs.mapper;

import com.historialplus.historialplus.dto.recordDTOs.request.RecordCreateDto;
import com.historialplus.historialplus.dto.recordDTOs.response.RecordResponseDto;
import com.historialplus.historialplus.dto.recordDetailDTOs.mapper.RecordDetailDtoMapper;
import com.historialplus.historialplus.entities.HospitalEntity;
import com.historialplus.historialplus.entities.PeopleEntity;
import com.historialplus.historialplus.entities.RecordEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecordDtoMapper {
    public static RecordResponseDto toResponseDto(RecordEntity recordEntity) {
        return new RecordResponseDto(
                recordEntity.getId(),
                recordEntity.getPerson().getName(),
                recordEntity.getHospital().getName(),
                recordEntity.getRecordDetails().stream()
                        .map(RecordDetailDtoMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
    }

    public static RecordEntity toEntity(RecordCreateDto recordCreateDto, PeopleEntity person, HospitalEntity hospital) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setPerson(person);
        recordEntity.setHospital(hospital);


        return recordEntity;
    }
}