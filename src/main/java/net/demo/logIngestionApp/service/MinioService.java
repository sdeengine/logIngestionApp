package net.demo.logIngestionApp.service;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MinioService {

    private MinioConfig minioConfig;

    @Value("${minio.server.bucket.name}")
    private String bucketName;

    public MinioService(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
    }

    private void createBucketIfNotExists() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found =
                minioConfig.minioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioConfig.minioClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } else {
            System.out.println("Bucket "+bucketName+" already exists.");
        }
    }

    public void uploadLogFile(String content) throws Exception {
        createBucketIfNotExists();
        String fileName = "logs_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt";

        // Write content to a temporary file
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {

            PutObjectArgs putObjectArgs =PutObjectArgs.builder()
                                                        .bucket(bucketName)
                                                        .object(fileName)
                                                        .stream(inputStream, inputStream.available(), -1)
                                                        .build();
            ObjectWriteResponse writeResponse = minioConfig.minioClient().putObject(putObjectArgs);

            System.out.println("Uploaded: " + writeResponse.toString());
        } catch (Exception e) {
            throw new RuntimeException("MinIO upload failed", e);
        }
    }
}