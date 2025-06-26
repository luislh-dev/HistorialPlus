package com.historialplus.historialplus.external.ilove_pdf.service;

import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.jobs.CompressPDFJob;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressPDFParams;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;
import com.adobe.pdfservices.operation.pdfjobs.result.CompressPDFResult;
import com.historialplus.historialplus.common.utils.SecureFileUtils;
import com.historialplus.historialplus.util.InMemoryMultipartFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class PDFCompressServiceImpl implements PDFCompressService {

    @Value("${adobe.client.id:fake}")
    private String clientId;

    @Value("${adobe.client.secret:fake}")
    private String clientSecret;

    @Override
    public MultipartFile compress(@NonNull MultipartFile file) throws IOException, ServiceApiException {
        CompressionLevel compressionLevel = CompressionLevel.HIGH;

        Path tempInputFile = SecureFileUtils.createSecureTempFileInDirectory("input-", ".pdf");
        file.transferTo(tempInputFile);

        try (InputStream inputStream = Files.newInputStream(tempInputFile)) {
            ServicePrincipalCredentials credentials = new ServicePrincipalCredentials(clientId, clientSecret);
            PDFServices pdfServices = new PDFServices(credentials);
            Asset asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.getMediaType());
            CompressPDFParams compressPDFParams = CompressPDFParams.compressPDFParamsBuilder()
                    .withCompressionLevel(compressionLevel)
                    .build();
            CompressPDFJob compressPDFJob = new CompressPDFJob(asset).setParams(compressPDFParams);
            String location = pdfServices.submit(compressPDFJob);
            PDFServicesResponse<CompressPDFResult> pdfServicesResponse = pdfServices.getJobResult(location, CompressPDFResult.class);
            Asset resultAsset = pdfServicesResponse.getResult().getAsset();
            StreamAsset streamAsset = pdfServices.getContent(resultAsset);

            byte[] compressedPDF = IOUtils.toByteArray(streamAsset.getInputStream());
            return new InMemoryMultipartFile(
                    "file",
                    UUID.randomUUID() + ".pdf",
                    "application/pdf",
                    compressedPDF
            );
        } finally {
            Files.deleteIfExists(tempInputFile);
        }
    }

}
