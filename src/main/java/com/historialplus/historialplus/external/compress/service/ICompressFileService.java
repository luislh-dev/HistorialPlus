package com.historialplus.historialplus.external.compress.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ICompressFileService {
    CompletableFuture<MultipartFile> compress(MultipartFile file);
}
