package com.historialplus.historialplus.internal.user.projection;

import com.historialplus.historialplus.common.constants.StateEnum;

import java.util.UUID;

public interface UserListProjection {
	UUID getId();
	String getUsername();
	String getEmail();
	String getDni();
	String getHospital();
	StateEnum getState();
	String getRoles();
}