package com.example.demo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.user}")
    private String minioUser;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.bucket}")
    private String minioBucket;

    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InterruptedException {
        boolean started = false;
        System.out.println("Wait for MinIO....");
        do {
            try {
                MinioClient minioClient = MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(minioUser, minioPassword)
                        .build();

                boolean isExists = minioClient.bucketExists(
                        BucketExistsArgs.builder().bucket(minioBucket).build()
                );

                if (!isExists)
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucket).build());

                return minioClient;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Thread.sleep(1000);
            }

        } while (!started);
        System.out.println("Connected to MinIO");

        return null;
    }

}
