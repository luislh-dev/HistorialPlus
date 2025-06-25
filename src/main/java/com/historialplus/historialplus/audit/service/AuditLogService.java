package com.historialplus.historialplus.audit.service;

import com.historialplus.historialplus.audit.context.AuditContext;

public interface AuditLogService {
	void logControllerAccess(AuditContext context, String requestPayload);
}
