package com.historialplus.historialplus.service.ImgCompressService;

import com.historialplus.historialplus.service.cloudflareservice.ICloudflareService;
import com.historialplus.historialplus.util.InMemoryMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class IMGCompressService {

    private final RestTemplate restTemplate;
    private final ICloudflareService cloudflareService;

    @Autowired
    public IMGCompressService(RestTemplate restTemplate, ICloudflareService cloudflareService) {
        this.restTemplate = restTemplate;
        this.cloudflareService = cloudflareService;
    }

    public String compressAndUploadImage(MultipartFile file, int quality) throws IOException {
        // Paso 1: Comprimir la imagen
        String url = "https://api-compress-image.fly.dev/compress?quality=" + quality;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        byte[] compressedImage = response.getBody();

        // Paso 2: Subir la imagen comprimida a Cloudflare R2
        MultipartFile compressedMultipartFile = new InMemoryMultipartFile(
                "file",
                UUID.randomUUID() + ".webp",
                "image/webp",
                compressedImage
        );

        try {
            return cloudflareService.uploadObject(compressedMultipartFile);
        } catch (Exception e) {
            throw new IOException("Error al subir la imagen a Cloudflare R2", e);
        }
    }
}