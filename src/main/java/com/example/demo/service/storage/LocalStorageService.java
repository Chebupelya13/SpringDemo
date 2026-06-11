package com.example.demo.service.storage;

import com.example.demo.enums.PhotoType;
import com.example.demo.enums.StorageType;
import com.example.demo.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@ConditionalOnProperty(
        name = "app.storage.type",
        havingValue = "local",
        matchIfMissing = true
)
public class LocalStorageService implements StorageService {

    private final PhotoService photoService;
    private static final Logger log = LoggerFactory.getLogger(LocalStorageService.class);

    public LocalStorageService(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCALE;
    }

    @Override
    public String saveFile(MultipartFile photo, PhotoType folder) {
        String extension = StringUtils.getFilenameExtension(photo.getOriginalFilename());
        String filename = "photos/" + folder.getFolderName() + "/" + UUID.randomUUID() + "." + extension;

        try (FileOutputStream photoFile = new FileOutputStream(filename)) {
            photoFile.write(photo.getBytes());
            filename = filename.replace("photos/", "");
            return filename;
        } catch (IOException e) {
            log.error("ошибка при сохранении файла ==> {}", e.getMessage());
            return null;
        }
    }

    @Override
    public InputStream getFile(String objectKey) {
        try {
            File file = new File("photos/" + objectKey);
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            log.error("ошибка при чтении файла ==> ", e);
            return null;
        }
    }

    @Override
    public void deleteFile(String objectKey) {
        try {
            Path filePath = Paths.get("/photos/" + objectKey);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("ошибка при удалении файла ==> ", e);
        }
    }

}
