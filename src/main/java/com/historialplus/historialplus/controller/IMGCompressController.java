package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.ImgCompressService.IMGCompressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<byte[]> compressImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "quality", defaultValue = "80") int quality) throws IOException {
        byte[] compressedImage = imgCompressService.compressImage(file, quality);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("image/webp"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(compressedImage);
    }
}