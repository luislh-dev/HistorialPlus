package com.historialplus.historialplus.controller;

import com.historialplus.historialplus.service.iLovePDFService.PDFCompressService;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/pdf")
public class PDFCompressController {

    @Autowired
    private PDFCompressService pdfCompressService;

    // Endpoint para comprimir PDF con nivel de compresión
    @PostMapping("/compress")
    public ResponseEntity<InputStreamResource> compressPDF(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "compressionLevel", defaultValue = "LOW") String compressionLevel) {
        try {
            // Convertir el archivo recibido en MultipartFile a Path
            Path tempInputFile = Files.createTempFile("input-", ".pdf");
            file.transferTo(tempInputFile);

            // Establecer el archivo de salida
            Path outputFile = Paths.get("output", "compressed-" + file.getOriginalFilename());

            // Convertir el nivel de compresión recibido (string) a un tipo enum CompressionLevel
            CompressionLevel level;
            try {
                level = CompressionLevel.valueOf(compressionLevel.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null); // Error si el nivel de compresión no es válido
            }

            // Llamar al servicio para comprimir el PDF con el nivel de compresión
            pdfCompressService.compressPDF(tempInputFile, outputFile, level);

            // Devolver el archivo comprimido como un flujo de entrada (InputStream)
            FileInputStream fileInputStream = new FileInputStream(outputFile.toFile());
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            // Retornar el archivo comprimido en la respuesta
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment;filename=" + outputFile.getFileName())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .contentLength(outputFile.toFile().length())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}