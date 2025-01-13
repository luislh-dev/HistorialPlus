package com.historialplus.historialplus.external.facade.CompressAndUploadService;

import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import com.historialplus.historialplus.external.compress.service.ICompressFileService;
import com.historialplus.historialplus.external.r2.service.ICloudflareService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CompressAndUploadServiceImpl implements ICompressAndUploadService {
    private static final Logger logger = LoggerFactory.getLogger(CompressAndUploadServiceImpl.class);

    private final ICompressFileService compressFileService;
    private final ICloudflareService cloudflareService;

    @Override
    public CompletableFuture<CompressFileDto> compressAndUpload(MultipartFile file) {

        return compressFileService.compress(file)
                .thenCompose(compressedFile -> {
                    try {
                        String objectKey = cloudflareService.uploadObject(compressedFile);
                        CompressFileDto dto = createCompressFileDto(compressedFile, objectKey);
                        return CompletableFuture.completedFuture(dto);
                    } catch (Exception e) {
                        logger.error("Error al subir el archivo comprimido a Cloudflare: {}", compressedFile.getOriginalFilename(), e);
                        throw new RuntimeException("Error al subir el archivo comprimido a Cloudflare", e);
                    }
                });
    }

    private CompressFileDto createCompressFileDto(MultipartFile compressedFile,  String objectKey) {
        CompressFileDto dto = new CompressFileDto();
        dto.setObjectKey(objectKey);
        dto.setSizeBytes(compressedFile.getSize());
        dto.setMimeType(compressedFile.getContentType());
        dto.setName(compressedFile.getOriginalFilename());

        logger.info("Archivo comprimido y subido exitosamente: {}, tamaño: {} bytes",
                compressedFile.getOriginalFilename(), compressedFile.getSize());

        return dto;
    }
}