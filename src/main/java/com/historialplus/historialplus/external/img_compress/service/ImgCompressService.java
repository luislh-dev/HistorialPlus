package com.historialplus.historialplus.external.img_compress.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImgCompressService {
    MultipartFile compressImage(MultipartFile file) throws IOException;
}