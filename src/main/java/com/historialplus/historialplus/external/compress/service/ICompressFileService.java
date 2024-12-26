package com.historialplus.historialplus.external.compress.service;

import com.historialplus.historialplus.external.compress.dto.CompressFileDto;
import org.springframework.web.multipart.MultipartFile;

public interface ICompressFileService {
    CompressFileDto compress(MultipartFile file);
}
