package com.historialplus.historialplus.service.cloudflareservice;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICloudflareService {
    List<String> listBuckets() throws Exception;
    String uploadObject(MultipartFile file) throws Exception;
    void deleteObjectByUrl(String url) throws Exception;
    void deleteObjectsByUrls(List<String> urls) throws Exception;
    void deleteObject(String objectKey) throws Exception;
}
