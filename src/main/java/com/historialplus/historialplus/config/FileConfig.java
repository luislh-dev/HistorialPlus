package com.historialplus.historialplus.config;

import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FileConfig {
	public static final long MAX_FILE_SIZE = 3 * 1024 * 1024;
	public static final List<String> ALLOWED_FILE_TYPES = List.of("image/jpeg", "image/png", "image/webp", "application/pdf");
}
