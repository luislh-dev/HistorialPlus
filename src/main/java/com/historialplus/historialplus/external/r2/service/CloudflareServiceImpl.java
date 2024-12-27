package com.historialplus.historialplus.external.r2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class CloudflareServiceImpl implements ICloudflareService{

    private final S3Client s3Client;
    private final long MAX_FILE_SIZE = 3 * 1024 * 1024;
    private final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp", "application/pdf");
    private final String BUCKET_NAME = "historialplus";

    public CloudflareServiceImpl() {
        this.s3Client = createS3Client();
    }

    private S3Client createS3Client() {
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID") != null ? System.getenv("AWS_ACCESS_KEY_ID") : "fake";
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY") != null ? System.getenv("AWS_SECRET_ACCESS_KEY") : "fake";
        String serviceUrl = System.getenv("SERVICE_CLOUDFLARE_R2_URL") != null ? System.getenv("SERVICE_CLOUDFLARE_R2_URL") : "http://localhost:9000";

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .endpointOverride(URI.create(serviceUrl))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public List<String> listBuckets() throws Exception {
        ListBucketsResponse response = s3Client.listBuckets();
        return response.buckets().stream()
                .map(Bucket::name)
                .toList();
    }

    @Override
    public String uploadObject(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío o no se proporciona");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("El tamaño del archivo excede el límite máximo de 500 KB");
        }

        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("No se permite ningún tipo de archivo. Solo se admiten JPEG, PNG y WebP.");
        }

        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("upload", null);
            Files.write(tempFile, file.getBytes());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromFile(tempFile));

            return String.format("https://luislh.dev/%s", file.getOriginalFilename());
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    @Override
    public void deleteObjectByUrl(String url) throws Exception {

    }

    @Override
    public void deleteObjectsByUrls(List<String> urls) throws Exception {

    }

    @Override
    public void deleteObject(String objectKey) throws Exception {

    }
}
