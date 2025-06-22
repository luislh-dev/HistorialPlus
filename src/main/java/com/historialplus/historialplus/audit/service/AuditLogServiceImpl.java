package com.historialplus.historialplus.audit.service;

import com.historialplus.historialplus.audit.context.AuditContext;
import com.historialplus.historialplus.audit.entities.AuditLogEntity;
import com.historialplus.historialplus.audit.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements IAuditLogService {
	private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

	private final AuditLogRepository repository;

	public AuditLogServiceImpl(AuditLogRepository repository) {
		this.repository = repository;
	}

	@Override
	@Async
	public void logControllerAccess(AuditContext context, String requestPayload) {
		AuditLogEntity logEntity = createLogEntity(context, requestPayload);
		try {
			repository.save(logEntity);
		} catch (Exception e) {
			logger.warn("No se pudo guardar el log de auditoría: {}", e.getMessage());
		}
	}

	private AuditLogEntity createLogEntity(AuditContext context, String requestPayload) {
		return AuditLogEntity.builder()
				.timestamp(context.timestamp())
				.username(context.username())
				.method(context.method())
				.endpoint(context.endpoint())
				.ipAddress(context.ipAddress())
				.requestPayload(requestPayload)
				.build();
	}
}
