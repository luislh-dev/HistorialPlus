package com.historialplus.historialplus.service.iLovePDFService;

import org.springframework.stereotype.Service;
import java.io.IOException;
import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.pdfjobs.jobs.CompressPDFJob;
import com.adobe.pdfservices.operation.pdfjobs.result.CompressPDFResult;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressPDFParams;
import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;

@Service
public class PDFCompressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFCompressService.class);

    // Asignar directamente las credenciales de prueba
    String CLIENT_ID = System.getenv("ADOBE_CLIENT_ID") != null ? System.getenv("ADOBE_CLIENT_ID") : "fake";
    String CLIENT_SECRET = System.getenv("ADOBE_CLIENT_SECRET") != null ? System.getenv("ADOBE_CLIENT_SECRET") : "fake";

    public void compressPDF(Path inputFile, Path outputFile, CompressionLevel compressionLevel) throws IOException {
        try (InputStream inputStream = Files.newInputStream(inputFile)) {
            // Crear credenciales usando los valores directamente en el código
            ServicePrincipalCredentials credentials = new ServicePrincipalCredentials(CLIENT_ID, CLIENT_SECRET);

            // Crear instancia de PDF Services
            PDFServices pdfServices = new PDFServices(credentials);

            // Subir archivo de entrada
            Asset asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.getMediaType());

            // Crear parámetros para la compresión con el nivel de compresión
            CompressPDFParams compressPDFParams = CompressPDFParams.compressPDFParamsBuilder()
                    .withCompressionLevel(compressionLevel.HIGH)
                    .build();

            // Crear trabajo de compresión
            CompressPDFJob compressPDFJob = new CompressPDFJob(asset)
                    .setParams(compressPDFParams);

            // Enviar trabajo y obtener ubicación del resultado
            String location = pdfServices.submit(compressPDFJob);
            PDFServicesResponse<CompressPDFResult> pdfServicesResponse = pdfServices.getJobResult(location, CompressPDFResult.class);

            // Obtener el archivo comprimido resultante
            Asset resultAsset = pdfServicesResponse.getResult().getAsset();
            StreamAsset streamAsset = pdfServices.getContent(resultAsset);

            // Crear directorio de salida si no existe
            Files.createDirectories(Paths.get("output"));

            // Escribir el archivo PDF comprimido
            try (OutputStream outputStream = Files.newOutputStream(outputFile)) {
                IOUtils.copy(streamAsset.getInputStream(), outputStream);
                LOGGER.info("Saved compressed PDF to " + outputFile);
            }
        } catch (ServiceApiException | IOException e) {
            LOGGER.error("Error occurred while compressing the PDF", e);
            throw new IOException("Error occurred while compressing the PDF", e);
        }
    }
}
