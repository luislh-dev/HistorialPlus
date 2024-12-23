package com.historialplus.historialplus.external.iLovePDF.controller;

import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;
import com.historialplus.historialplus.external.iLovePDF.service.PDFCompressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/pdf")
public class PDFCompressController {


    private final PDFCompressService pdfCompressService;

    public PDFCompressController(PDFCompressService pdfCompressService) {
        this.pdfCompressService = pdfCompressService;
    }

    @PostMapping("/compress")
    public ResponseEntity<?> compressPDF(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "compressionLevel", defaultValue = "LOW") String compressionLevel) {
        try {
            // Convertir MultipartFile a archivo temporal
            Path tempInputFile = Files.createTempFile("input-", ".pdf");
            file.transferTo(tempInputFile);

            // Convertir nivel de compresión recibido (String) a CompressionLevel
            CompressionLevel level;
            try {
                level = CompressionLevel.valueOf(compressionLevel.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid compression level. Use LOW, MEDIUM, or HIGH.");
            }

            // Llamar al servicio para comprimir y subir el PDF a R2
            String fileUrl = pdfCompressService.compressAndUploadPDF(tempInputFile, level);

            // Eliminar archivo temporal
            Files.deleteIfExists(tempInputFile);

            // Retornar la URL del archivo comprimido
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the PDF. Details: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}