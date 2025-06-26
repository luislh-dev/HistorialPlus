package com.historialplus.historialplus.external.facade.compress_upload;

import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface CompressAndUploadService {
    CompletableFuture<CompressFileDto> compressAndUpload(MultipartFile file);
}
