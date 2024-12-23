package com.historialplus.historialplus.external.iLovePDF.service;

import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.jobs.CompressPDFJob;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressPDFParams;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;
import com.adobe.pdfservices.operation.pdfjobs.result.CompressPDFResult;
import com.historialplus.historialplus.external.r2.service.ICloudflareService;
import com.historialplus.historialplus.util.InMemoryMultipartFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class PDFCompressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFCompressService.class);

    @Autowired
    private ICloudflareService cloudflareService;

    String CLIENT_ID = System.getenv("ADOBE_CLIENT_ID") != null ? System.getenv("ADOBE_CLIENT_ID") : "fake";
    String CLIENT_SECRET = System.getenv("ADOBE_CLIENT_SECRET") != null ? System.getenv("ADOBE_CLIENT_SECRET") : "fake";

    public String compressAndUploadPDF(Path inputFile, CompressionLevel compressionLevel) throws Exception {
        try (InputStream inputStream = Files.newInputStream(inputFile)) {
            ServicePrincipalCredentials credentials = new ServicePrincipalCredentials(CLIENT_ID, CLIENT_SECRET);
            PDFServices pdfServices = new PDFServices(credentials);
            Asset asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.getMediaType());
            CompressPDFParams compressPDFParams = CompressPDFParams.compressPDFParamsBuilder()
                    .withCompressionLevel(compressionLevel)
                    .build();
            CompressPDFJob compressPDFJob = new CompressPDFJob(asset).setParams(compressPDFParams);
            String location = pdfServices.submit(compressPDFJob); // Aquí puede lanzarse Exception
            PDFServicesResponse<CompressPDFResult> pdfServicesResponse = pdfServices.getJobResult(location, CompressPDFResult.class);
            Asset resultAsset = pdfServicesResponse.getResult().getAsset();
            StreamAsset streamAsset = pdfServices.getContent(resultAsset);

            byte[] compressedPDF = IOUtils.toByteArray(streamAsset.getInputStream());
            MultipartFile compressedMultipartFile = new InMemoryMultipartFile(
                    "file",
                    UUID.randomUUID() + ".pdf",
                    "application/pdf",
                    compressedPDF
            );

            return cloudflareService.uploadObject(compressedMultipartFile);
        }
    }
}
