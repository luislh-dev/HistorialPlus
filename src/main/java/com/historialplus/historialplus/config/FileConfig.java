package com.historialplus.historialplus.config;

import java.util.List;

public class FileConfig {
	private FileConfig() {}

	public static final long MAX_FILE_SIZE = 3L * 1024 * 1024;
	public static final List<String> ALLOWED_FILE_TYPES = List.of("image/jpeg", "image/png", "image/webp", "application/pdf");
}
