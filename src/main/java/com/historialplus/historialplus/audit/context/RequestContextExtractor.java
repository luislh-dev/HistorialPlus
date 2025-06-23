package com.historialplus.historialplus.audit.context;

import com.historialplus.historialplus.audit.util.IpUtils;
import com.historialplus.historialplus.auth.AuthService.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.historialplus.historialplus.common.enums.TimeZoneEnum.LIMA;

@Component
@RequiredArgsConstructor
public class RequestContextExtractor implements IRequestContextExtractor{
	private final HttpServletRequest request;
	private final IAuthService authService;

	@Override
	public AuditContext extract() {
		return AuditContext.builder()
			.username(authService.getUsername())
			.method(request.getMethod())
			.endpoint(getEndpoint())
			.ipAddress(IpUtils.extractClientIp(request))
			.timestamp(ZonedDateTime.now(ZoneId.of(LIMA.getZoneId())).toInstant())
			.build();
	}

	private String getEndpoint() {
		return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
	}

}
