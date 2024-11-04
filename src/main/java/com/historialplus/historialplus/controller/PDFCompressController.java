package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.iLovePDFService.PDFCompressService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PDFCompressController {

    private final PDFCompressService pdfCompressService;

    public PDFCompressController(PDFCompressService pdfCompressService) {
        this.pdfCompressService = pdfCompressService;
    }

    @PostMapping("/compress")
    public ResponseEntity<byte[]> compressFiles(@RequestBody String[] filePaths) {
        try {
            // Llama al servicio para comprimir archivos
            byte[] compressedFile = pdfCompressService.compressFiles(filePaths);

            // Configura los encabezados de respuesta para retornar el PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "compressed.pdf");

            return new ResponseEntity<>(compressedFile, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
