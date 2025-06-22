package com.historialplus.historialplus.audit.context;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AuditContext(
	String username,
	String method,
	String endpoint,
	String ipAddress,
	Instant timestamp
) { }
