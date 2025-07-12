package com.historialplus.historialplus.internal.hospital.projection;

import com.historialplus.historialplus.common.enums.StateEnum;

public interface HospitalPageProjection {
	Integer getId();
	String getName();
	String getPhone();
	String getEmail();
	String getRuc();
	StateEnum getState();
}
