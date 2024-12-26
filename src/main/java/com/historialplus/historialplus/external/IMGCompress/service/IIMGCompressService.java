package com.historialplus.historialplus.external.IMGCompress.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IIMGCompressService {
    MultipartFile compressImage(MultipartFile file) throws IOException;
}