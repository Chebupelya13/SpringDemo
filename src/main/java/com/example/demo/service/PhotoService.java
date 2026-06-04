package com.example.demo.service;

import com.example.demo.dao.PhotoDao;
import com.example.demo.entity.Photo;
import com.example.demo.enums.PhotoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PhotoService {

    private final PhotoDao photoDao;
    private final MinioService minioService;

    @Autowired
    public PhotoService(PhotoDao photoDao, MinioService minioService) {
        this.photoDao = photoDao;
        this.minioService = minioService;
    }

    public Photo getPhotoById(int photoId) {
        return photoDao.getPhotoById(photoId);
    }

    public void addPhoto(MultipartFile photo_file, PhotoType type) {
        String objectKey = minioService.uploadFile(photo_file, type);

        Photo photo = new Photo(objectKey, type);
        photoDao.addPhoto(photo);
    }

}
