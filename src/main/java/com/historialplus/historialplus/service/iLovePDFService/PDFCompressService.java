package com.historialplus.historialplus.service.iLovePDFService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PDFCompressService {

    @Autowired
    private RestTemplate restTemplate;

    public byte[] compressFiles(String[] filePaths) {
        String url = "http://localhost:5000/api/IlovePDF/compress";

        // Configura los encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la entidad HTTP con el cuerpo de la solicitud
        HttpEntity<String[]> requestEntity = new HttpEntity<>(filePaths, headers);

        // Realiza la solicitud y recibe la respuesta
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        // Retorna el archivo comprimido como un array de bytes
        return response.getBody();
    }
}
