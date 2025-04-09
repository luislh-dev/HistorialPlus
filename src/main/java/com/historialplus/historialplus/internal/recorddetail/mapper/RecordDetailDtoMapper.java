package com.historialplus.historialplus.internal.recorddetail.mapper;

import com.historialplus.historialplus.internal.file.mapper.FilesPresenter;
import com.historialplus.historialplus.internal.file.presenter.FileDetailPresenter;
import com.historialplus.historialplus.internal.file.projection.FileBasicProjection;
import com.historialplus.historialplus.internal.recorddetail.dto.response.RecordDetailResponseDto;
import com.historialplus.historialplus.internal.recorddetail.entites.RecordDetailEntity;
import com.historialplus.historialplus.internal.recorddetail.presenters.RecordDetailPresenter;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailExtenseProjection;
import com.historialplus.historialplus.internal.recorddetail.projection.RecordDetailProjection;
import com.historialplus.historialplus.internal.recorddetail.viewmodel.RecordDetailExtenseViewModel;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static com.historialplus.historialplus.common.constants.SexTypeEnum.MALE;

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

        String doctorPrefix = (Objects.equals(projection.getSexTypeName(), MALE.name())) ? "Dr. " : "Dra. ";
        String doctorFullName = doctorPrefix + projection.getDoctorFullName();

        String visitDate = (projection.getVisitDate() != null) ? projection.getVisitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : null;

        List<FileDetailPresenter> files = orDefault.stream().map(FilesPresenter::toFileDetailPresenter).toList();

        return new RecordDetailPresenter(
                projection.getId(),
                projection.getReason(),
                visitDate,
                projection.getHospitalName(),
                doctorFullName,
                files
        );
    }

    // Convertir de RecordDetailExtenseProjection y una lista de FileBasicProjection a RecordDetailExtensePresenter
    public static RecordDetailExtenseViewModel toPresenter(RecordDetailExtenseProjection projection, List<FileBasicProjection> orDefault) {

        String doctorPrefix = (Objects.equals(projection.getDoctorSexTypeName(), MALE)) ? "Dr. " : "Dra. ";
        String doctorFullName = doctorPrefix + projection.getDoctorName() + " " + projection.getDoctorPaternalSurname() + " " + projection.getDoctorMaternalSurname();

        String visitDate = (projection.getVisitDate() != null) ? projection.getVisitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : null;

        // Convertir la lista de FileBasicProjection a una lista de FileDetailPresenter
        List<FileDetailPresenter> files = orDefault.stream().map(FilesPresenter::toFileDetailPresenter).toList();

        return RecordDetailExtenseViewModel.builder()
                .id(projection.getId())
                .doctorFullName(doctorFullName)
                .hospitalName(projection.getHospitalName())
                .visitDate(visitDate)
                .reason(projection.getReason())
                .diagnostic(projection.getDiagnostic())
                .treatment(projection.getTreatment())
                .files(files)
                .build();
    }
}