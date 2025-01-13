package com.historialplus.historialplus.external.r2.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.historialplus.historialplus.config.FileConfig.ALLOWED_FILE_TYPES;
import static com.historialplus.historialplus.config.FileConfig.MAX_FILE_SIZE;

@Service
public class CloudflareServiceImpl implements ICloudflareService {

	@Value("${service.cloudflare.r2.url}")
	private String serviceUrl;

	@Value("${service.cloudflare.bucket.name}")
	private String bucketName;

	@Value("${aws.access.key.id}")
	private String accessKeyId;

	@Value("${aws.secret.access.key}")
	private String secretAccessKey;

	private S3Client s3Client;

	@PostConstruct
	private void init() {
		this.s3Client = createS3Client();
	}

	private S3Client createS3Client() {
		if (accessKeyId == null || secretAccessKey == null) {
			throw new IllegalArgumentException("No se proporcionaron las credenciales de AWS");
		}

		if (serviceUrl == null) {
			throw new IllegalArgumentException("No se proporcionó la URL del servicio de Cloudflare");
		}

		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

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
				.bucket(bucketName)
				.key(file.getOriginalFilename())
				.contentType(file.getContentType())
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
