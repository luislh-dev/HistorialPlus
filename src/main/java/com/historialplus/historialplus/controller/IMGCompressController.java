package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.ImgCompressService.IMGCompressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IMGCompressController {

    private final IMGCompressService imgCompressService;

    @Autowired
    public IMGCompressController(IMGCompressService imgCompressService) {
        this.imgCompressService = imgCompressService;
    }

    @PostMapping("/api/compress-image")
    public ResponseEntity<Map<String, Object>> compressAndUploadImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "quality", defaultValue = "80") int quality) throws IOException {
        Map<String, Object> response = new HashMap<>();

        try {
            String imageUrl = imgCompressService.compressAndUploadImage(file, quality);
            response.put("success", true);
            response.put("url", imageUrl);
            response.put("message", "Archivo comprimido y subido exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}