package com.historialplus.historialplus.external.iLovePDF.service;

import com.adobe.pdfservices.operation.exception.ServiceApiException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PDFCompressService {
    MultipartFile compress(MultipartFile file) throws IOException, ServiceApiException;
}
