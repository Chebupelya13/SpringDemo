package com.example.demo.service.storage;

import com.example.demo.dao.FilesDao;
import com.example.demo.enums.PhotoType;
import com.example.demo.enums.StorageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@ConditionalOnProperty(
        name = "app.storage.type",
        havingValue = "database",
        matchIfMissing = true
)
@Transactional
public class DatabaseStorageService implements StorageService {

    private final FilesDao filesDao;
    private static final Logger log = LoggerFactory.getLogger(DatabaseStorageService.class);

    public DatabaseStorageService(FilesDao filesDao) {
        this.filesDao = filesDao;
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.DATABASE;
    }

    @Override
    public String saveFile(MultipartFile file, PhotoType folder) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = folder.getFolderName() + "/" + UUID.randomUUID() + extension;

        try {
            filesDao.addFile(filename, file.getBytes());
            return filename;
        } catch (IOException e ) {
            log.error("ошибка при попытке получения bytes из MultipartFile ==> ", e);
            return "";
        }
    }

    @Override
    public InputStream getFile(String objectKey) {
        return new ByteArrayInputStream(filesDao.getFile(objectKey).getPhoto());
    }

    @Override
    public void deleteFile(String objectKey) {
        filesDao.deleteFile(objectKey);
    }

}
