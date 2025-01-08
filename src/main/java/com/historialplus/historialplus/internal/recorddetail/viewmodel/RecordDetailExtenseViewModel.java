package com.historialplus.historialplus.internal.recorddetail.viewmodel;

import com.historialplus.historialplus.internal.file.presenter.FileDetailPresenter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RecordDetailExtenseViewModel {

	private UUID id;
	private String doctorFullName;
	private String hospitalName;
	private String visitDate;
	private String reason;
	private String diagnostic;
	private String treatment;
	private List<FileDetailPresenter> files;

}
