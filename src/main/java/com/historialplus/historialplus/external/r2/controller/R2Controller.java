package com.historialplus.historialplus.external.r2.controller;

import com.historialplus.historialplus.external.r2.service.ICloudflareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/r2")
@RequiredArgsConstructor
public class R2Controller {

    private final ICloudflareService cloudflareService;

    @GetMapping("/buckets")
    public List<String> listBuckets() throws Exception {
        return cloudflareService.listBuckets();
    }

    @GetMapping("/presigned-url")
    public String generatePresignedUrl(@RequestParam String objectKey) throws Exception {
        return cloudflareService.generatePresignedUrl(objectKey);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            String url = cloudflareService.uploadObject(file);

            response.put("success", true);
            response.put("message", "Archivo subido exitosamente");
            response.put("url", url);
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("contentType", file.getContentType());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al procesar el archivo: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
