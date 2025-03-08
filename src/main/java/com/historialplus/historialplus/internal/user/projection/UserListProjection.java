package com.historialplus.historialplus.internal.user.projection;

import java.util.UUID;

public interface UserListProjection {
	UUID getId();
	String getUsername();
	String getEmail();
	String getDni();
	String getHospital();
	String getState();
	String getRoles();
}