package com.historialplus.historialplus.audit.aspect;

import com.historialplus.historialplus.audit.context.RequestContextExtractor;
import com.historialplus.historialplus.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
	private final AuditLogService auditLogService;
	private final RequestContextExtractor requestContextExtractor;

	@AfterReturning(pointcut = "execution(* com.historialplus.historialplus.internal.*.controller..*(..))")
	public void logAccess(JoinPoint joinPoint) {
		String requestPayload = Arrays.stream(joinPoint.getArgs())
			.map(arg -> arg != null ? arg.toString() : "")
			.collect(Collectors.joining(", "));

		auditLogService.logControllerAccess(requestContextExtractor.extract(), requestPayload);
	}
}
