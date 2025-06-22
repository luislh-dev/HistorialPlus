package com.historialplus.historialplus.audit.context;

import com.historialplus.historialplus.auth.AuthService.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.historialplus.historialplus.common.constants.HttpHeadersCustom.X_FORWARDED_FOR;
import static com.historialplus.historialplus.common.constants.HttpHeadersCustom.X_VERCEL_FORWARDED_FOR;
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
			.ipAddress(extractIpAddress())
			.timestamp(ZonedDateTime.now(ZoneId.of(LIMA.getZoneId())).toInstant())
			.build();
	}

	private String getEndpoint() {
		return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
	}

	private String extractIpAddress() {
		return Optional.ofNullable(request.getHeader(X_VERCEL_FORWARDED_FOR))
			.or(() -> Optional.ofNullable(request.getHeader(X_FORWARDED_FOR)))
			.map(h -> h.split(",")[0].trim())
			.orElse(request.getRemoteAddr());
	}
}
