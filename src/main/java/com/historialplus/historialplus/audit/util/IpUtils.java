package com.historialplus.historialplus.audit.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.historialplus.historialplus.common.constants.HttpHeadersCustom.*;

@Slf4j
@UtilityClass
public class IpUtils {
	private static final List<String> PROXY_HEADERS = List.of(
		X_VERCEL_FORWARDED_FOR,
		X_FORWARDED_FOR,
		X_REAL_IP,
		X_CLIENT_IP,
		CF_CONNECTING_IP,
		X_FORWARDED,
		FORWARDED_FOR,
		FORWARDED
	);

	private static final String UNKNOWN = "unknown";

	public static String extractClientIp(HttpServletRequest request) {
		Objects.requireNonNull(request, "HttpServletRequest no puede ser null");

		for (String header : PROXY_HEADERS) {
			String headerValue = request.getHeader(header);
			if (isValidHeaderValue(headerValue)) {
				Optional<String> validIp = extractFirstValidIp(headerValue);
				if (validIp.isPresent()) {
					log.debug("IP obtenida del header {}: {}", header, validIp.get());
					return validIp.get();
				}
			}
		}

		String remoteAddr = request.getRemoteAddr();
		if (isValidIPv4(remoteAddr) || isValidIPv6(remoteAddr)) {
			log.debug("IP obtenida de RemoteAddr: {}", remoteAddr);
			return remoteAddr;
		}

		log.warn("No se pudo determinar la IP del cliente");
		return UNKNOWN;
	}

	private static Optional<String> extractFirstValidIp(String headerValue) {
		return Arrays.stream(headerValue.split(","))
			.map(String::trim)
			.filter(ip -> !ip.isEmpty() && !ip.equalsIgnoreCase(UNKNOWN))
			.filter(ip -> isValidIPv4(ip) || isValidIPv6(ip))
			.findFirst();
	}


	private static boolean isValidHeaderValue(String headerValue) {
		return headerValue != null &&
			   !headerValue.trim().isEmpty() &&
			   !headerValue.trim().equalsIgnoreCase(UNKNOWN);
	}

	public static boolean isValidIPv4(String ip) {
		if (ip == null || ip.trim().isEmpty()) {
			return false;
		}

		String[] parts = ip.split("\\.");
		if (parts.length != 4) {
			return false;
		}

		try {
			for (String part : parts) {
				int octet = Integer.parseInt(part);
				if (octet < 0 || octet > 255) {
					return false;
				}
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isValidIPv6(String ip) {
		if (ip == null || ip.trim().isEmpty()) {
			return false;
		}

		String ipv6Pattern = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|" +
							 "^([0-9a-fA-F]{1,4}:)*::([0-9a-fA-F]{1,4}:)*[0-9a-fA-F]{1,4}$|" +
							 "^::1$|^::$";

		return ip.matches(ipv6Pattern);
	}
}
