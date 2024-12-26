package com.historialplus.historialplus.external.IMGCompress.service;

import com.historialplus.historialplus.util.InMemoryMultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

/**
 * Servicio para comprimir imágenes mediante una API externa.
 */
@Service
@RequiredArgsConstructor
public class IMGCompressService implements IIMGCompressService {

    private final RestTemplate restTemplate;

    /**
     * Comprime un archivo de imagen enviándolo a una API externa.
     *
     * @param file el archivo de imagen que se comprimirá
     * @return un MultipartFile que contiene la imagen comprimida
     * @throws IOException si ocurre un error de E/S
     */
    public MultipartFile compressImage(MultipartFile file) throws IOException {
        int quality = 80;

        String url = "https://api-compress-image.fly.dev/compress?quality=" + quality;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        byte[] compressedImage = response.getBody();
        return new InMemoryMultipartFile("file", file.getOriginalFilename(), "image/webp", compressedImage);
    }

}