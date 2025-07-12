package com.historialplus.historialplus.audit.context;

import com.historialplus.historialplus.audit.util.IpUtils;
import com.historialplus.historialplus.auth.service.AuthService;
import com.historialplus.historialplus.util.TimeUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

@Component
@RequiredArgsConstructor
public class RequestContextExtractorImpl implements RequestContextExtractor{
	private final HttpServletRequest request;
	private final AuthService authService;

	@Override
	public AuditContext extract() {
		return AuditContext.builder()
			.username(authService.getUsername())
			.method(request.getMethod())
			.endpoint(getEndpoint())
			.ipAddress(IpUtils.extractClientIp(request))
			.timestamp(TimeUtils.getCurrentInstant())
			.build();
	}

	private String getEndpoint() {
		return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
	}

}
