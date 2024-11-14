package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.ImgCompressService.IMGCompressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
public class IMGCompressController {
    private final IMGCompressService imgCompressService;

    @Autowired
    public IMGCompressController(IMGCompressService imgCompressService) {
        this.imgCompressService = imgCompressService;
    }

    @PostMapping("/api/compress-image")
    public String compressImage(@RequestParam("file") MultipartFile file, @RequestParam("quality") int quality) throws IOException {
        return imgCompressService.compressImage(file, quality);
    }
}