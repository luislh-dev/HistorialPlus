package com.historialplus.historialplus.audit.service;

import com.historialplus.historialplus.audit.context.AuditContext;

public interface IAuditLogService {
	void logControllerAccess(AuditContext context, String requestPayload);
}
