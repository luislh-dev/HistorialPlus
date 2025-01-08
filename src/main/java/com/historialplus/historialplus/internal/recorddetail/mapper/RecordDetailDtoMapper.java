package com.historialplus.historialplus.internal.recorddetail.mapper;

import com.historialplus.historialplus.internal.file.entites.FileTypeEntity;
import com.historialplus.historialplus.internal.file.presenter.FileDetailPresenter;
import com.historialplus.historialplus.internal.file.projection.FileBasicProjection;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.presenters.RecordDetailPresenter;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailProjection;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.common.constants.SexTypeConstants.MALE_ID;

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

    // Cambiar la presentacion de los datos de RecordDetailProjection a RecordDetailPresenter
    public static RecordDetailPresenter toPresenter(RecordDetailProjection projection, List<FileBasicProjection> orDefault) {
        return new RecordDetailPresenter(
                projection.getId(),
                projection.getReason(),
                (projection.getVisitDate() != null ? projection.getVisitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : null),
                projection.getHospitalName(),
                (Objects.equals(projection.getSexTypeId(), MALE_ID) ? "Dr. " : "Dra. ") + projection.getDoctorFullName(),
                (orDefault != null ? orDefault.stream().map(f -> {
                    String typeName;
                    try {
                        typeName = FileTypeEntity.FileType.valueOf(f.getTypeName()).getDisplayName();
                    } catch (IllegalArgumentException e) {
                        typeName = f.getTypeName();
                    }
                    return new FileDetailPresenter(
                            f.getName(),
                            (f.getSizeInBytes() > 1024 ?
                                    (f.getSizeInBytes() > 1024 * 1024 ?
                                            String.format("%.2f MB", f.getSizeInBytes() / (1024.0 * 1024)) :
                                            String.format("%.2f KB", f.getSizeInBytes() / 1024.0)) :
                                    f.getSizeInBytes() + " B"),
                            f.getUrl(),
                            typeName,
                            f.getMimeType()
                    );
                }).collect(Collectors.toList()) : null)
        );
    }

}