package com.historialplus.historialplus.external.r2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICloudflareService {
    List<String> listBuckets() throws Exception;
    String uploadObject(MultipartFile file) throws IOException;
    String generatePresignedUrl(String objectKey) throws Exception;
    void deleteObjectByUrl(String url) throws Exception;
    void deleteObjectsByUrls(List<String> urls) throws Exception;
    void deleteObject(String objectKey) throws Exception;
}
