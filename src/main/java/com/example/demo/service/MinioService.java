package com.example.demo.service;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinioService {

    private final MinioClient minioClient;

    @Autowired
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }



}
