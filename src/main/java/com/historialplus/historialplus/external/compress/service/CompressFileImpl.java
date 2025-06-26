package com.historialplus.historialplus.external.compress.service;

import com.historialplus.historialplus.error.exceptions.ExternalServiceException;
import com.historialplus.historialplus.external.ilove_pdf.service.PDFCompressService;
import com.historialplus.historialplus.external.img_compress.service.ImgCompressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CompressFileImpl implements CompressFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompressFileImpl.class);

    private final ImgCompressService imgCompressService;
    private final PDFCompressService pdfCompressService;

    @Override
    public CompletableFuture<MultipartFile> compress(MultipartFile file) {

        if (file == null) {
            LOGGER.error("Se intentó comprimir un archivo nulo");
            return CompletableFuture.failedFuture(new IllegalArgumentException("El archivo no puede ser nulo"));
        }

        String contentType = file.getContentType();
        LOGGER.info("Intentando comprimir archivo de tipo: {}", contentType);

        try {
            if (contentType != null && contentType.startsWith("image/")) {
                LOGGER.debug("Procesando imagen: {}", file.getOriginalFilename());
                return compressImage(file);
            } else if ("application/pdf".equals(contentType)) {
                LOGGER.debug("Procesando PDF: {}", file.getOriginalFilename());
                return compressPDF(file);
            } else {
                String errorMsg = String.format("Tipo de archivo no soportado: %s", contentType);
                LOGGER.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        } catch (Exception e) {
            throw new ExternalServiceException("Error al procesar el archivo", e);
        }
    }

    private CompletableFuture<MultipartFile> compressImage(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return imgCompressService.compressImage(file);
            } catch (IOException e) {
                throw new ExternalServiceException("Error al procesar el archivo", e);
            }
        });
    }

    private CompletableFuture<MultipartFile> compressPDF(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return pdfCompressService.compress(file);
            } catch (Exception e) {
                throw new ExternalServiceException("Error al procesar el archivo", e);
            }
        });
    }
}