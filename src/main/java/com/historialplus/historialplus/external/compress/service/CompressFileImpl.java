package com.historialplus.historialplus.external.compress.service;

import com.historialplus.historialplus.external.IMGCompress.service.IIMGCompressService;
import com.historialplus.historialplus.external.iLovePDF.service.IPDFCompressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CompressFileImpl implements ICompressFileService {
    private static final Logger logger = LoggerFactory.getLogger(CompressFileImpl.class);

    private final IIMGCompressService imgCompressService;
    private final IPDFCompressService pdfCompressService;

    @Override
    public CompletableFuture<MultipartFile> compress(MultipartFile file) {

        if (file == null) {
            logger.error("Se intentó comprimir un archivo nulo");
            return CompletableFuture.failedFuture(new IllegalArgumentException("El archivo no puede ser nulo"));
        }

        String contentType = file.getContentType();
        logger.info("Intentando comprimir archivo de tipo: {}", contentType);

        try {
            if (contentType != null && contentType.startsWith("image/")) {
                logger.debug("Procesando imagen: {}", file.getOriginalFilename());
                return compressImage(file);
            } else if ("application/pdf".equals(contentType)) {
                logger.debug("Procesando PDF: {}", file.getOriginalFilename());
                return compressPDF(file);
            } else {
                String errorMsg = String.format("Tipo de archivo no soportado: %s", contentType);
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        } catch (Exception e) {
            logger.error("Error al comprimir el archivo: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Error al procesar el archivo", e);
        }
    }

    private CompletableFuture<MultipartFile> compressImage(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return imgCompressService.compressImage(file);
            } catch (IOException e) {
                logger.error("Error al comprimir la imagen: {}", file.getOriginalFilename(), e);
                throw new RuntimeException("Error al procesar el archivo", e);
            }
        });
    }

    private CompletableFuture<MultipartFile> compressPDF(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return pdfCompressService.compress(file);
            } catch (Exception e) {
                logger.error("Error al comprimir el PDF: {}", file.getOriginalFilename(), e);
                throw new RuntimeException("Error al procesar el archivo", e);
            }
        });
    }
}