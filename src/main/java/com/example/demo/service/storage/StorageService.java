package com.example.demo.service.storage;

import com.example.demo.enums.PhotoType;
import com.example.demo.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {
    StorageType getStorageType();

    String saveFile(MultipartFile file, PhotoType folder);

    InputStream getFile(String objectKey);

    void deleteFile(String objectKey);
}
