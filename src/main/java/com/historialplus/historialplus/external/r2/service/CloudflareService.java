package com.historialplus.historialplus.external.r2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudflareService {
    String uploadObject(MultipartFile file) throws IOException;
    String generatePresignedUrl(String objectKey);
}
