package com.example.demo.service.storage;

import com.example.demo.enums.PhotoType;
import com.example.demo.enums.StorageType;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@ConditionalOnProperty(
        name = "app.storage.type",
        havingValue = "minio"
)
public class MinioService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.MINIO;
    }

    @Override
    public String saveFile(MultipartFile file, PhotoType folder) {
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String objectKey = folder.getFolderName() + "/" + UUID.randomUUID() + (extension != null ? "." + extension : "");

            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return objectKey;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO", e);
        }
    }

    @Override
    public InputStream getFile(String objectKey) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error getting file from MinIO", e);
        }
    }

    @Override
    public void deleteFile(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO", e);
        }
    }

}
