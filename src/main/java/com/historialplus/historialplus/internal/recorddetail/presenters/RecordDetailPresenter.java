package com.historialplus.historialplus.internal.recorddetail.presenters;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.historialplus.historialplus.internal.file.presenter.FileDetailPresenter;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RecordDetailPresenter {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("visit_date")
    private String visitDate;

    @JsonProperty("hospital_name")
    private String hospitalName;

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("files")
    private List<FileDetailPresenter> files;

    public RecordDetailPresenter(UUID id, String reason, String visitDate, String hospitalName, String doctorName, List<FileDetailPresenter> files) {
        this.id = id;
        this.reason = reason;
        this.visitDate = visitDate;
        this.hospitalName = hospitalName;
        this.doctorName = doctorName;
        this.files = files;
    }


}