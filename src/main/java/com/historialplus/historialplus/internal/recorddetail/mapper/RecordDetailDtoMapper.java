package com.historialplus.historialplus.internal.recorddetail.mapper;

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
                // Verificar el tipo de sexo del doctor para agregar el prefijo Dr. o Dra.
                (Objects.equals(projection.getSexTypeId(), MALE_ID) ? "Dr. " : "Dra. ") + projection.getDoctorFullName(),
                // Mapear los archivos de la proyeccion a la presentacion
                (orDefault != null ? orDefault.stream().map(f ->
                        new RecordDetailPresenter.FileDetailPresenter(
                                f.getName(),
                                // Cambiar el tamaño del archivo a KB o MB si es mayor a 1 KB o 1 MB respectivamente
                                (f.getSizeInBytes() > 1024 ?
                                        (f.getSizeInBytes() > 1024 * 1024 ?
                                                String.format("%.2f MB", f.getSizeInBytes() / (1024.0 * 1024)) :
                                                String.format("%.2f KB", f.getSizeInBytes() / 1024.0)) :
                                        f.getSizeInBytes() + " B"),
                                f.getUrl(),
                                f.getTypeName(),
                                f.getMimeType()
                        )
                ).collect(Collectors.toList()) : null)
        );
    }

}