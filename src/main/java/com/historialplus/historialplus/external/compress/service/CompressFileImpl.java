package com.historialplus.historialplus.external.compress.service;

import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.historialplus.historialplus.external.IMGCompress.service.IIMGCompressService;
import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import com.historialplus.historialplus.external.iLovePDF.service.IPDFCompressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class CompressFileImpl implements ICompressFileService {
    private static final Logger logger = LoggerFactory.getLogger(CompressFileImpl.class);

    private final IIMGCompressService imgCompressService;
    private final IPDFCompressService pdfCompressService;

    @Override
    public CompressFileDto compress(MultipartFile file) {
        if (file == null) {
            logger.error("Se intentó comprimir un archivo nulo");
            throw new IllegalArgumentException("El archivo no puede ser nulo");
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

    private CompressFileDto compressImage(MultipartFile file) throws IOException {
        try {
            MultipartFile compressedImage = imgCompressService.compressImage(file);
            return createCompressFileDto(compressedImage);
        } catch (IOException e) {
            logger.error("Error al comprimir la imagen: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

    private CompressFileDto compressPDF(MultipartFile file) throws IOException, ServiceApiException {
        try {
            MultipartFile compressedPDF = pdfCompressService.compress(file);
            return createCompressFileDto(compressedPDF);
        } catch (Exception e) {
            logger.error("Error al comprimir el PDF: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

    private CompressFileDto createCompressFileDto(MultipartFile compressedFile) {
        CompressFileDto dto = new CompressFileDto();
        dto.setPreviewUrl(compressedFile.getOriginalFilename());
        dto.setSizeBytes(compressedFile.getSize());
        dto.setMimeType(compressedFile.getContentType());
        dto.setName(compressedFile.getOriginalFilename());

        logger.info("Archivo comprimido exitosamente: {}, tamaño: {} bytes",
                compressedFile.getOriginalFilename(), compressedFile.getSize());

        return dto;
    }
}