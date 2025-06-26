package com.historialplus.historialplus.common.utils;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class SecureFileUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecureFileUtils.class);

	private SecureFileUtils() {}

	public static Path createSecureTempFileInDirectory(String prefix, String suffix) throws IOException {

		if (SystemUtils.IS_OS_UNIX) {
			FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------"));
			return Files.createTempFile(prefix, suffix, attr);
		} else {
			File file = Files.createTempFile(prefix, suffix).toFile();

			if (!file.setReadable(false, false)) {
				LOGGER.warn("No se pudo quitar permisos de lectura globales");
			}
			if (!file.setWritable(false, false)) {
				LOGGER.warn("No se pudo quitar permisos de escritura globales");
			}
			if (!file.setExecutable(false, false)) {
				LOGGER.warn("No se pudo quitar permisos de ejecución globales");
			}

			if (!file.setReadable(true, true)) {
				throw new IOException("Failed to set file as readable for owner");
			}
			if (!file.setWritable(true, true)) {
				throw new IOException("Failed to set file as writable for owner");
			}

			return file.toPath();
		}
	}

}
